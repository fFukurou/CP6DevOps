package br.com.greeks.greeks.domain.deitytype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDeityTypeDto(
        @NotBlank(message = "Type name cannot be blank.")
        @Size(max = 100)
        String typeName,

        @Size(max = 250)
        String origin,

        @Size(max = 500)
        String description,

        @Size(max = 100)
        String lifespan,

        @Size(max = 150)
        String powerSource
) {}