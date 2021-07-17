package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Flux<Student> findStudentsByName(String name) {
        return (name != null) ? studentRepository.findByName(name) : studentRepository.findAll();
    }

    public Mono<Student> findStudentById(long id) {
        return studentRepository.findById(id);
    }

    public Mono<Student> addNewStudent(Student student) {
        return studentRepository.save(student);
    }

    public Mono<Student> updateStudent(long id, Student student) {
        return studentRepository.findById(id)
                .flatMap(s -> {
                    student.setId(s.getId());
                    return studentRepository.save(student);
                });

    }

    public Mono<Void> deleteStudent(Student student) {
        return studentRepository.delete(student);
    }

}
