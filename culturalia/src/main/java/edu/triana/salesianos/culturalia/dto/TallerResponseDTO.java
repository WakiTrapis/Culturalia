package edu.triana.salesianos.culturalia.dto;

import edu.triana.salesianos.culturalia.model.Taller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TallerResponseDTO {

    private Long id;
    private String titulo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private OrganizadorResponseDTO organizador;
    private List<ParticipanteResponseDTO> participantes;

    public static TallerResponseDTO of(Taller taller) {
        if (taller == null) return null;

        return TallerResponseDTO.builder()
                .id(taller.getId())
                .titulo(taller.getTitulo())
                .fechaHoraInicio(LocalDateTime.parse(taller.getFechaHoraInicio()))
                .fechaHoraFin(LocalDateTime.parse(taller.getFechaHoraFin()))
                .organizador(OrganizadorResponseDTO.of(taller.getOrganizador()))
                .participantes(taller.getParticipantes() != null ?
                        taller.getParticipantes().stream()
                                .map(ParticipanteResponseDTO::of)
                                .toList() : List.of())
                .build();
    }
}
