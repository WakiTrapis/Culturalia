package edu.triana.salesianos.culturalia.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizador {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

    @OneToMany
    private List<Taller> talleres = new ArrayList<>();
}
