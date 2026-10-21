package edu.triana.salesianos.culturalia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taller {

    @Id
    @GeneratedValue
    private Long id;
    private String titulo;
    private String fechaHoraInicio;
    private String fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private Organizador organizador;

    @ManyToMany
    @JoinTable(
            name = "taller_participantes",
            joinColumns = @JoinColumn(name = "taller_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    @Builder.Default
    private HashSet<Participante> participantes = new HashSet<>();

}
