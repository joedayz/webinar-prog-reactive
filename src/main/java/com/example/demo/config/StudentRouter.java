package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class StudentRouter {

    @Bean
    public RouterFunction<ServerResponse> route(StudentHandler studentHandler){
        return RouterFunctions.route(GET("/students/{id:[0-9]+}").and(accept(APPLICATION_JSON)), studentHandler::getStudent)
                .andRoute(
                        GET("/students")
                                .and(accept(APPLICATION_JSON)), studentHandler::listStudents)
                .andRoute(
                        POST("/students")
                                .and(accept(APPLICATION_JSON)),studentHandler::addNewStudent)
                .andRoute(
                        PUT("students/{id:[0-9]+}")
                                .and(accept(APPLICATION_JSON)), studentHandler::updateStudent)
                .andRoute(
                        DELETE("/students/{id:[0-9]+}")
                                .and(accept(APPLICATION_JSON)), studentHandler::deleteStudent);
    }
}
