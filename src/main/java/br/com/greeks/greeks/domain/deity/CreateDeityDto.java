package br.com.greeks.greeks.domain.deity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDeityDto(
        @NotBlank(message = "Name cannot be blank.")
        @Size(max = 100)
        String name,

        @Size(max = 150)
        String domain,

        @Size(max = 100)
        String romanName,

        @Size(max = 500)
        String description,

        @NotNull(message = "Deity Type ID is required.")
        Long deityTypeId
) {}