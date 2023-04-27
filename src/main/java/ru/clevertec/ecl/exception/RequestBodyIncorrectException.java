package ru.clevertec.ecl.exception;

public class RequestBodyIncorrectException extends RuntimeException{
    public RequestBodyIncorrectException() {
    }

    public RequestBodyIncorrectException(String message) {
        super(message);
    }
}
