package edu.triana.salesianos.culturalia.error.notFound;

public class ParticipanteNotFoundException extends NotFoundException {

    public ParticipanteNotFoundException(String message) {
        super(message);
    }

    public ParticipanteNotFoundException(Long id) {
        super("No se ha encontrado ningún participante con el ID: " + id);
    }
}
