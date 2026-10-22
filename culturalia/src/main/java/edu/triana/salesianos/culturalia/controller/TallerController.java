package edu.triana.salesianos.culturalia.controller;

import edu.triana.salesianos.culturalia.dto.CrearTallerDTO;
import edu.triana.salesianos.culturalia.dto.ParticipanteResponseDTO;
import edu.triana.salesianos.culturalia.dto.TallerResponseDTO;
import edu.triana.salesianos.culturalia.service.TallerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talleres")
@RequiredArgsConstructor
@Tag(name = "Taller", description = "Endpoints para la gestión de talleres culturales")
public class TallerController {

    private final TallerService tallerService;

    @Operation(summary = "Crear un nuevo taller", description = "Asocia un nuevo taller a un organizador existente.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Taller creado con éxito",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TallerResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Taller Creado",
                                    value = """
                                    {
                                      "id": 1,
                                      "titulo": "Taller de Fotografía",
                                      "fechaHoraInicio": "2026-11-10T10:00:00",
                                      "fechaHoraFin": "2026-11-10T13:00:00",
                                      "organizador": {
                                        "id": 1,
                                        "nombre": "Asociación Cultural Triana",
                                        "email": "contacto@culturatriana.org"
                                      },
                                      "participantes": []
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Organizador no encontrado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<TallerResponseDTO> create(@Valid @RequestBody CrearTallerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tallerService.save(dto));
    }

    @Operation(summary = "Obtener todos los talleres", description = "Devuelve el catálogo completo de talleres.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de talleres",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TallerResponseDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existen talleres",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<TallerResponseDTO>> findAll() {
        return ResponseEntity.ok(tallerService.findAll());
    }


    @Operation(summary = "Obtener taller por ID", description = "Devuelve la información detallada de un taller por su ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Taller encontrado",
                    content = @Content(schema = @Schema(implementation = TallerResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Taller no encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TallerResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tallerService.findById(id));
    }


    @Operation(summary = "Inscribir un participante en un taller", description = "Asocia un participante existente a la lista de inscritos de un taller.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Participante inscrito con éxito",
                    content = @Content(schema = @Schema(implementation = TallerResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Taller o Participante no encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "El participante ya se encuentra inscrito en el taller",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<TallerResponseDTO> addParticipante(
            @PathVariable Long tallerId,
            @PathVariable Long participanteId) {
        return ResponseEntity.ok(tallerService.addParticipanteToTaller(tallerId, participanteId));
    }

    @Operation(summary = "participantes de un taller", description = "Busca un taller por Id y muestra los participantes inscritos en ese taller.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de participantes o lista vacia."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Taller no encontrado"
            )
    })
    @GetMapping("/{tallerId}/participantes")
    public ResponseEntity<List<ParticipanteResponseDTO>> getParticipantesByTallerId(@PathVariable Long tallerId) {
        return ResponseEntity.ok(tallerService.getParticipantesByTallerId(tallerId));
    }



    @Operation(summary = "Eliminar la inscripción de un participante", description = "Desvincula a un participante de un taller sin eliminar ninguna de las dos entidades.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Inscripción eliminada o no existía vinculación previa"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Taller o Participante no encontrado"
            )
    })
    @DeleteMapping("/{tallerId}/participantes/{participanteId}")
    public ResponseEntity<Void> removeParticipante(
            @PathVariable Long tallerId,
            @PathVariable Long participanteId) {
        tallerService.removeParticipanteFromTaller(tallerId, participanteId);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Editar un taller existente",
            description = "Actualiza los datos de un taller existente según su ID. Valida que el título no esté duplicado y que la fecha de fin sea posterior a la de inicio."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Taller actualizado con éxito",
                    content = @Content(schema = @Schema(implementation = TallerResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos o título ya existente",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Taller u Organizador no encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TallerResponseDTO> edit(
            @PathVariable Long id,
            @Valid @RequestBody CrearTallerDTO dto) {
        return ResponseEntity.ok(tallerService.edit(id, dto));
    }


    @Operation(
            summary = "Eliminar un taller",
            description = "Elimina un taller existente según su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Taller eliminado con éxito (Sin contenido)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Taller no encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tallerService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
