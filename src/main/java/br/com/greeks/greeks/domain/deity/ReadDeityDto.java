package br.com.greeks.greeks.domain.deity;

import br.com.greeks.greeks.domain.deitytype.ReadDeityTypeDto;

public record ReadDeityDto(
        Long id,
        String name,
        String domain,
        String romanName,
        String description,
        ReadDeityTypeDto deityType
) {

    public ReadDeityDto(Deity deity) {
        this(
                deity.getId(),
                deity.getName(),
                deity.getDomain(),
                deity.getRomanName(),
                deity.getDescription(),

                deity.getDeityType() != null ? new ReadDeityTypeDto(deity.getDeityType()) : null
        );
    }
}