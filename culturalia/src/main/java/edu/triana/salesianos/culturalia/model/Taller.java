package edu.triana.salesianos.culturalia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Setter
@Getter
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
    @JoinColumn(name = "organizador_id", nullable = true)
    private Organizador organizador;

    @ManyToMany
    @JoinTable(
            name = "taller_participantes",
            joinColumns = @JoinColumn(name = "taller_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    @Builder.Default
    private Set<Participante> participantes = new HashSet<>();

}
