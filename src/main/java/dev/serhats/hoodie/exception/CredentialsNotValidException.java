package dev.serhats.hoodie.exception;

public class CredentialsNotValidException extends RuntimeException {
    public CredentialsNotValidException() {
        super("Credentials are not valid!");
    }
}
