package edu.triana.salesianos.culturalia.validation;

import edu.triana.salesianos.culturalia.dto.CrearTallerDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechaFinPosteriorAInicioValidator implements ConstraintValidator<FechaFinPosteriorAInicio, CrearTallerDTO> {

    @Override
    public boolean isValid(CrearTallerDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getFechaHoraInicio() == null || dto.getFechaHoraFin() == null) {
            return true;
        }
        return dto.getFechaHoraFin().isAfter(dto.getFechaHoraInicio());
    }
}
