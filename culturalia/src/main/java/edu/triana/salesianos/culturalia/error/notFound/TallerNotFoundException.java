package edu.triana.salesianos.culturalia.error.notFound;

public class TallerNotFoundException extends NotFoundException {

    public TallerNotFoundException(String message) {
        super(message);
    }

    public TallerNotFoundException(Long id) {
        super("No se ha encontrado ningún taller con el ID: " + id);
    }
}
