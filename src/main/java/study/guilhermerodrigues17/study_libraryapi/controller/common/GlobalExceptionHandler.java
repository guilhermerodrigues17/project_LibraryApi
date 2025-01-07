package study.guilhermerodrigues17.study_libraryapi.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.ExceptionFields;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.ExceptionResponse;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ExceptionFields> exceptionFields = fieldErrors.stream().map(
                fieldError -> new ExceptionFields(
                        fieldError.getField(),
                        fieldError.getDefaultMessage())
        ).toList();

        return new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error!",
                exceptionFields
        );
    }
}
