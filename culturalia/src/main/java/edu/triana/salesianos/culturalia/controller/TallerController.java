package edu.triana.salesianos.culturalia.controller;

import edu.triana.salesianos.culturalia.dto.CrearTallerDTO;
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
}
