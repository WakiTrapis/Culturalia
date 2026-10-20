package edu.triana.salesianos.culturalia.repository;

import edu.triana.salesianos.culturalia.model.Taller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TallerRepository extends JpaRepository<Taller, Long> {

    boolean existsByTitulo(String titulo);

}