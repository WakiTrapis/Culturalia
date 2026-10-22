package edu.triana.salesianos.culturalia.repository;

import edu.triana.salesianos.culturalia.model.Taller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TallerRepository extends JpaRepository<Taller, Long> {

    boolean existsByTitulo(String titulo);

    @Query("SELECT DISTINCT t FROM Taller t LEFT JOIN FETCH t.organizador LEFT JOIN FETCH t.participantes")
    List<Taller> findAllWithAssociations();

    @Query("SELECT DISTINCT t FROM Taller t LEFT JOIN FETCH t.organizador LEFT JOIN FETCH t.participantes WHERE t.id = :id")
    Optional<Taller> findByIdWithAssociations(Long id);

}