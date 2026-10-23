package edu.triana.salesianos.culturalia.error.notFound;

public abstract class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
