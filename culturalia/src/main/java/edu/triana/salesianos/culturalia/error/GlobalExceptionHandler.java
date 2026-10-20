package edu.triana.salesianos.culturalia.error;

import edu.triana.salesianos.culturalia.error.notFound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Recurso No Encontrado");
        problemDetail.setType(URI.create("https://culturalia.es/errors/not-found"));
        return problemDetail;
    }
    @ExceptionHandler(TallerWasFoundException.class)
    public ProblemDetail handleTallerYaExisteException(TallerWasFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setTitle("Taller Ya Existente");
        problemDetail.setType(URI.create("https://culturalia.es/errors/taller-ya-existe"));
        return problemDetail;
    }
    @ExceptionHandler(ParticipanteWasFoundException.class)
    public ProblemDetail handleParticipanteYaExiste(ParticipanteWasFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problem.setTitle("Participante Ya Existente");
        problem.setType(URI.create("https://culturalia.es/errors/participante-ya-existe"));
        return problem;
    }
}
