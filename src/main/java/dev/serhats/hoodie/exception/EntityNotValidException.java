package dev.serhats.hoodie.exception;

public class EntityNotValidException extends RuntimeException {
    public EntityNotValidException() {
        super("Entity is not valid!");
    }

    public EntityNotValidException(String entityName) {
        super(String.format("%s entity is not valid!", entityName));
    }

    public EntityNotValidException(String entityName, String fieldName) {
        super(String.format("%s field of %s entity is not valid!", fieldName, entityName));
    }
}
