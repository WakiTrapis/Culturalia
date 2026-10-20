package edu.triana.salesianos.culturalia.controller;

import edu.triana.salesianos.culturalia.dto.CrearOrganizadorDTO;
import edu.triana.salesianos.culturalia.dto.OrganizadorResponseDTO;
import edu.triana.salesianos.culturalia.service.OrganizadorService;
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
@RequestMapping("/organizadores")
@RequiredArgsConstructor
@Tag(name = "Organizador", description = "Endpoints para la gestión de organizadores de talleres")
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    @Operation(
            summary = "Crear un nuevo organizador",
            description = "Registra un nuevo organizador en el sistema a partir de sus datos de entrada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Organizador creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrganizadorResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de organizador creado",
                                    summary = "Respuesta tras crear un organizador",
                                    value = """
                                    {
                                      "id": 1,
                                      "nombre": "Asociación Cultural Triana",
                                      "email": "contacto@culturatriana.org"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error de validación",
                                    summary = "Error cuando el email o nombre no cumplen el formato",
                                    value = """
                                    {
                                      "type": "about:blank",
                                      "title": "Bad Request",
                                      "status": 400,
                                      "detail": "Invalid request content.",
                                      "instance": "/organizadores"
                                    }
                                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<OrganizadorResponseDTO> create(@Valid @RequestBody CrearOrganizadorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organizadorService.save(dto));
    }


    @Operation(
            summary = "Obtener todos los organizadores",
            description = "Devuelve el listado completo de organizadores registrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de organizadores",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = OrganizadorResponseDTO.class)
                            ),
                            examples = @ExampleObject(
                                    name = "Listado de organizadores",
                                    summary = "Ejemplo de respuesta con varios organizadores",
                                    value = """
                                    [
                                      {
                                        "id": 1,
                                        "nombre": "Asociación Cultural Triana",
                                        "email": "contacto@culturatriana.org"
                                      },
                                      {
                                        "id": 2,
                                        "nombre": "Ateneo Artístico de Sevilla",
                                        "email": "info@ateneoartistico.es"
                                      }
                                    ]
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron organizadores registrados",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    summary = "Mensaje informando que no existen organizadores",
                                    value = """
                                    {
                                      "type": "https://example.com/errors/organizador-not-found",
                                      "title": "Recurso no encontrado",
                                      "status": 404,
                                      "detail": "No se han encontrado organizadores",
                                      "instance": "/organizadores"
                                    }
                                    """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<OrganizadorResponseDTO>> findAll() {
        return ResponseEntity.ok(organizadorService.findAll());
    }
}
