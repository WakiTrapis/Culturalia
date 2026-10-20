package edu.triana.salesianos.culturalia.error;

public class ParticipanteAlreadyRegisteredException extends RuntimeException {
    public ParticipanteAlreadyRegisteredException(Long tallerId, Long participanteId) {
        super("El participante con ID " + participanteId + " ya se encuentra inscrito en el taller con ID " + tallerId);
    }
}