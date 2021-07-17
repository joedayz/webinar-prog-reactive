package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {

    public Flux<Student> findByName(String name);
}
