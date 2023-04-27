package ru.clevertec.ecl.exception;

public class ResourceNotFountException extends RuntimeException{
    public ResourceNotFountException(String message, Throwable cause) {
        super(message, cause);
    }


    public ResourceNotFountException(String message) {
        super(message);
    }

    public ResourceNotFountException() {
    }
}
