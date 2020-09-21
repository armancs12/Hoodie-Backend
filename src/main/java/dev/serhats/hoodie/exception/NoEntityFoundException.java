package dev.serhats.hoodie.exception;

public class NoEntityFoundException extends RuntimeException {
    public NoEntityFoundException() {
        super("No entity found!");
    }

    public NoEntityFoundException(String entityName) {
        super(String.format("No %s entity found!", entityName));
    }
}
