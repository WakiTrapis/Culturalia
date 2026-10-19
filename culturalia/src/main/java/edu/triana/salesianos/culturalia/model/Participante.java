package edu.triana.salesianos.culturalia.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participante {

    @Id
    @GeneratedValue
    private int id;
    private String nombreCompleto;

    @ManyToMany
    private Taller taller;
}
