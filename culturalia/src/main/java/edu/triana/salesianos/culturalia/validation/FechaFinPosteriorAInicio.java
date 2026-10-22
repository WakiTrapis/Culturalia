package edu.triana.salesianos.culturalia.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechaFinPosteriorAInicioValidator.class)
@Documented
public @interface FechaFinPosteriorAInicio {

    String message() default "La fecha y hora de fin debe ser posterior a la fecha y hora de inicio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
