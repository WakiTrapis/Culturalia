package edu.triana.salesianos.culturalia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taller {

    @Id
    @GeneratedValue
    private int id;
    private String titulo;
    private String fechaHoraInicio;
    private String fechaHoraFin;

    @ManyToOne
    private Organizador organizador;

    @ManyToMany
    private List<Participante> participantes = new ArrayList<>();

}
