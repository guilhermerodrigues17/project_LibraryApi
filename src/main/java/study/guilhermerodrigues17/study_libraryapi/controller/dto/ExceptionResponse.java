package study.guilhermerodrigues17.study_libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ExceptionResponse(Integer status, String message, List<ExceptionFields> errors) {

    public static ExceptionResponse defaultResponse(String message) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ExceptionResponse conflictResponse(String message) {
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
