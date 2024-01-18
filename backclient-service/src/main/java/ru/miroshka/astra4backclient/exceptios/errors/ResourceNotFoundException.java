package ru.miroshka.astra4backclient.exceptios.errors;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
