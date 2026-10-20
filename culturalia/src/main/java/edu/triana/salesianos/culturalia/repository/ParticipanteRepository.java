package edu.triana.salesianos.culturalia.repository;

import edu.triana.salesianos.culturalia.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    boolean existsByNombreCompleto(String nombreCompleto);
}
