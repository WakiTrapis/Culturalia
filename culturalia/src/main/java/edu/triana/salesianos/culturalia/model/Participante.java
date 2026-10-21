package edu.triana.salesianos.culturalia.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;


@Entity
@Data
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
    private HashSet<Taller> talleres = new HashSet<>();
}
