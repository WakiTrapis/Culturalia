package edu.triana.salesianos.culturalia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearOrganizadorDTO {

    @NotBlank(message = "{organizador.nombre.notblank}")
    private String nombre;

    @NotBlank(message = "{organizador.email.notblank}")
    @Email(message = "{organizador.email.email}")
    private String email;
}
