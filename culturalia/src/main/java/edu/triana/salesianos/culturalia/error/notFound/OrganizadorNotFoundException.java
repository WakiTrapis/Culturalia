package edu.triana.salesianos.culturalia.error.notFound;

public class OrganizadorNotFoundException extends NotFoundException {

    public OrganizadorNotFoundException(String message) {
        super(message);
    }

    public OrganizadorNotFoundException(Long id) {
        super("No se ha encontrado ningún organizador con el ID: " + id);
    }
}
