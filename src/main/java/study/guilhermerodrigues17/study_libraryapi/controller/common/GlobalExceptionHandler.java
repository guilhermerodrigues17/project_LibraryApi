package study.guilhermerodrigues17.study_libraryapi.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.ExceptionFields;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.ExceptionResponse;
import study.guilhermerodrigues17.study_libraryapi.exceptions.DuplicatedRegisterException;
import study.guilhermerodrigues17.study_libraryapi.exceptions.InvalidFieldException;
import study.guilhermerodrigues17.study_libraryapi.exceptions.NotAllowedOperationException;

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

    @ExceptionHandler(DuplicatedRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDuplicatedRegisterException(DuplicatedRegisterException e) {
        return ExceptionResponse.conflictResponse(e.getMessage());
    }

    @ExceptionHandler(NotAllowedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleNotAllowedOperationException(NotAllowedOperationException e) {
        return ExceptionResponse.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerException(RuntimeException e) {
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred!",
                List.of()
        );
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleInvalidFieldException(InvalidFieldException e) {
        return new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error",
                List.of(new ExceptionFields(e.getField(), e.getMessage()))
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleAccessDeniedException(AccessDeniedException e) {
        return new ExceptionResponse(
                HttpStatus.FORBIDDEN.value(),
                "Access Denied!",
                List.of()
        );
    }
}
