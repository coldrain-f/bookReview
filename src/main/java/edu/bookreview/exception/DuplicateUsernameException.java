package edu.bookreview.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        super();
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
