package study.guilhermerodrigues17.study_libraryapi.exceptions;

public class NotAllowedOperationException extends RuntimeException {
    public NotAllowedOperationException(String message) {
        super(message);
    }
}
