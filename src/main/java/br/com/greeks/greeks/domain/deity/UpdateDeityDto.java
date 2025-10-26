package br.com.greeks.greeks.domain.deity;

import jakarta.validation.constraints.Size;

public record UpdateDeityDto(
        @Size(max = 100, message = "Name must be less than 100 characters.")
        String name,

        @Size(max = 150)
        String domain,

        @Size(max = 100)
        String romanName,

        @Size(max = 500)
        String description,

        Long deityTypeId
) {}