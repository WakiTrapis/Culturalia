package edu.triana.salesianos.culturalia.dto;

import edu.triana.salesianos.culturalia.model.Organizador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizadorResponseDTO {

    private Long id;
    private String nombre;
    private String email;

    public static OrganizadorResponseDTO of(Organizador organizador) {
        if (organizador == null) {
            return null;
        }
        return OrganizadorResponseDTO.builder()
                .id(organizador.getId())
                .nombre(organizador.getNombre())
                .email(organizador.getEmail())
                .build();
    }
}