package edu.triana.salesianos.culturalia.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participante {

    @Id
    @GeneratedValue
    private Long id;
    private String nombreCompleto;

    @ManyToMany(mappedBy = "participantes")
    @Builder.Default
    private Set<Taller> talleres = new HashSet<>();
}
