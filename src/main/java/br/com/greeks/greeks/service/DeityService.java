package br.com.greeks.greeks.service;


import br.com.greeks.greeks.domain.deity.*;
import br.com.greeks.greeks.domain.deitytype.DeityType;
import br.com.greeks.greeks.domain.deitytype.DeityTypeRepository;
import br.com.greeks.greeks.exception.DuplicateEntryException;
import br.com.greeks.greeks.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeityService {

    @Autowired
    private final DeityRepository deityRepository;

    @Autowired
    private final DeityTypeRepository deityTypeRepository;

    @Transactional
    public ReadDeityDto create(CreateDeityDto dto) {

        deityRepository.findByNameIgnoreCase(dto.name()).ifPresent(existing -> {
            throw new DuplicateEntryException("Deity name already exists: " + dto.name());
        });


        DeityType deityType = deityTypeRepository.findById(dto.deityTypeId())
                .orElseThrow(() -> new NotFoundException("Deity Type not found with ID: " + dto.deityTypeId()));

        Deity deity = new Deity();
        deity.setName(dto.name());
        deity.setDomain(dto.domain());
        deity.setRomanName(dto.romanName());
        deity.setDescription(dto.description());
        deity.setDeityType(deityType);

        Deity saved = deityRepository.save(deity);

        Deity reloaded = deityRepository.findByIdWithDeityType(saved.getId()).orElse(saved);
        return new ReadDeityDto(reloaded);
    }

    public ReadDeityDto findById(Long id) {

        return deityRepository.findByIdWithDeityType(id)
                .map(ReadDeityDto::new)
                .orElseThrow(() -> new NotFoundException("Deity not found with ID: " + id));
    }

    public Page<ReadDeityDto> findAllFiltered(String name, String domain, Long deityTypeId, Pageable pageable) {

         return deityRepository.findAllFiltered(name, domain, deityTypeId, pageable)
                .map(ReadDeityDto::new);
    }

    @Transactional
    public ReadDeityDto update(Long id, UpdateDeityDto dto) {
        Deity deity = deityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deity not found with ID: " + id));


        if (dto.name() != null && !dto.name().equalsIgnoreCase(deity.getName())) {
            if (deityRepository.existsByNameIgnoreCaseAndIdNot(dto.name(), id)) {
                throw new DuplicateEntryException("Deity name already exists: " + dto.name());
            }
            deity.setName(dto.name());
        }


        if (dto.domain() != null) deity.setDomain(dto.domain());
        if (dto.romanName() != null) deity.setRomanName(dto.romanName());
        if (dto.description() != null) deity.setDescription(dto.description());

        if (dto.deityTypeId() != null && (deity.getDeityType() == null || !dto.deityTypeId().equals(deity.getDeityType().getId()))) {
            DeityType newDeityType = deityTypeRepository.findById(dto.deityTypeId())
                    .orElseThrow(() -> new NotFoundException("Deity Type not found with ID: " + dto.deityTypeId()));
            deity.setDeityType(newDeityType);
        }

        Deity saved = deityRepository.save(deity);

        Deity reloaded = deityRepository.findByIdWithDeityType(saved.getId()).orElse(saved);
        return new ReadDeityDto(reloaded);
    }

    @Transactional
    public void delete(Long id) {
        if (!deityRepository.existsById(id)) {
            throw new NotFoundException("Deity not found with ID: " + id);
        }
        deityRepository.deleteById(id);

    }
}