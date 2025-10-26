package br.com.greeks.greeks.domain.deitytype;

import jakarta.validation.constraints.Size;


public record UpdateDeityTypeDto(
        @Size(max = 100, message = "Type name must be less than 100 characters.")
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