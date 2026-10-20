package edu.triana.salesianos.culturalia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearTallerDTO {

    @NotBlank(message = "{taller.titulo.notblank}")
    private String titulo;

    @NotNull(message = "{taller.fechainicio.notnull}")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "{taller.fechafin.notnull}")
    private LocalDateTime fechaHoraFin;

    @NotNull(message = "{taller.organizador.notnull}")
    private Long organizadorId;
}
