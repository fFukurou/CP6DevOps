package br.com.greeks.greeks.domain.deitytype;

public record ReadDeityTypeDto(
        Long id,
        String typeName,
        String origin,
        String description,
        String lifespan,
        String powerSource
) {

    public ReadDeityTypeDto(DeityType deityType) {
        this(
                deityType.getId(),
                deityType.getTypeName(),
                deityType.getOrigin(),
                deityType.getDescription(),
                deityType.getLifespan(),
                deityType.getPowerSource()
        );
    }
}