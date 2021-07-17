package com.example.demo.config;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class StudentHandler {

    private StudentService studentService;

    public StudentHandler(StudentService studentService) {
        this.studentService = studentService;
    }
    public Mono<ServerResponse> getStudent(ServerRequest serverRequest) {
        Mono<Student> studentMono = studentService.findStudentById(
                Long.parseLong(serverRequest.pathVariable("id")));
        return studentMono.flatMap(student -> ServerResponse.ok()
                .body(fromValue(student)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listStudents(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse(null);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.findStudentsByName(name), Student.class);
    }

    public Mono<ServerResponse> addNewStudent(ServerRequest serverRequest) {
        Mono<Student> studentMono = serverRequest.bodyToMono(Student.class);
        return studentMono.flatMap(student ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.addNewStudent(student), Student.class));

    }

    public Mono<ServerResponse> updateStudent(ServerRequest serverRequest) {
        final long studentId = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<Student> studentMono = serverRequest.bodyToMono(Student.class);

        return studentMono.flatMap(student ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.updateStudent(studentId, student), Student.class));
    }

    public Mono<ServerResponse> deleteStudent(ServerRequest serverRequest) {
        final long studentId = Long.parseLong(serverRequest.pathVariable("id"));
        return studentService
                .findStudentById(studentId)
                .flatMap(s -> ServerResponse.noContent().build(studentService.deleteStudent(s)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
