package edu.triana.salesianos.culturalia.controller;

import edu.triana.salesianos.culturalia.dto.CrearParticipanteDTO;
import edu.triana.salesianos.culturalia.dto.ParticipanteResponseDTO;
import edu.triana.salesianos.culturalia.service.ParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/participantes")
@RequiredArgsConstructor
@Tag(name = "Participante", description = "Endpoints para la gestión de participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @Operation(summary = "Crear participante", description = "Registra un nuevo participante en la plataforma.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Participante creado correctamente",
                    content = @Content(schema = @Schema(implementation = ParticipanteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> create(@Valid @RequestBody CrearParticipanteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(participanteService.save(dto));
    }


    @Operation(summary = "Listar participantes", description = "Obtiene todos los participantes registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ParticipanteResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No hay participantes registrados",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<ParticipanteResponseDTO>> findAll() {
        return ResponseEntity.ok(participanteService.findAll());
    }


    @Operation(summary = "Buscar participante por ID", description = "Devuelve el detalle de un participante específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Participante encontrado",
                    content = @Content(schema = @Schema(implementation = ParticipanteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Participante no encontrado",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(participanteService.findById(id));
    }
}
