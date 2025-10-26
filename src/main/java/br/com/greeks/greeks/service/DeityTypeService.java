package br.com.greeks.greeks.service;

import br.com.greeks.greeks.domain.deitytype.*;
import br.com.greeks.greeks.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeityTypeService {

    @Autowired
    private final DeityTypeRepository deityTypeRepository;

    @Transactional
    public ReadDeityTypeDto create(CreateDeityTypeDto dto) {

        deityTypeRepository.findByTypeNameIgnoreCase(dto.typeName()).ifPresent(existing -> {
            throw new DuplicateEntryException("Deity Type name already exists: " + dto.typeName());
        });

        DeityType deityType = new DeityType();
        deityType.setTypeName(dto.typeName());
        deityType.setOrigin(dto.origin());
        deityType.setDescription(dto.description());
        deityType.setLifespan(dto.lifespan());
        deityType.setPowerSource(dto.powerSource());

        DeityType saved = deityTypeRepository.save(deityType);
        return new ReadDeityTypeDto(saved);
    }

    public ReadDeityTypeDto findById(Long id) {
        return deityTypeRepository.findById(id)
                .map(ReadDeityTypeDto::new)
                .orElseThrow(() -> new NotFoundException("Deity Type not found with ID: " + id));
    }


    public Page<ReadDeityTypeDto> findAllFiltered(String typeName, String origin, Pageable pageable) {
        return deityTypeRepository.findAllFiltered(typeName, origin, pageable)
                .map(ReadDeityTypeDto::new);
    }

    @Transactional
    public ReadDeityTypeDto update(Long id, UpdateDeityTypeDto dto) {
        DeityType deityType = deityTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deity Type not found with ID: " + id));


        if (dto.typeName() != null && !dto.typeName().equalsIgnoreCase(deityType.getTypeName())) {
            if (deityTypeRepository.existsByTypeNameIgnoreCaseAndIdNot(dto.typeName(), id)) {
                throw new DuplicateEntryException("Deity Type name already exists: " + dto.typeName());
            }
            deityType.setTypeName(dto.typeName());
        }


        if (dto.origin() != null) deityType.setOrigin(dto.origin());
        if (dto.description() != null) deityType.setDescription(dto.description());
        if (dto.lifespan() != null) deityType.setLifespan(dto.lifespan());
        if (dto.powerSource() != null) deityType.setPowerSource(dto.powerSource());

        DeityType updated = deityTypeRepository.save(deityType);
        return new ReadDeityTypeDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!deityTypeRepository.existsById(id)) {
            throw new NotFoundException("Deity Type not found with ID: " + id);
        }
        deityTypeRepository.deleteById(id);
    }
}