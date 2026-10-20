package edu.triana.salesianos.culturalia.dto;

import edu.triana.salesianos.culturalia.model.Participante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipanteResponseDTO {

    private Long id;
    private String nombreCompleto;

    public static ParticipanteResponseDTO of(Participante participante) {
        if (participante == null) {
            return null;
        }
        return ParticipanteResponseDTO.builder()
                .id(participante.getId())
                .nombreCompleto(participante.getNombreCompleto())
                .build();
    }
}
