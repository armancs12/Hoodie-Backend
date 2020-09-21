package dev.serhats.hoodie.exception;

public class NoEntityFoundException extends RuntimeException {
    public NoEntityFoundException(String entityName) {
        super(String.format("No %s Entity Found!", entityName));
    }
}
