package br.com.greeks.greeks;

import br.com.greeks.greeks.domain.deitytype.DeityType;
import br.com.greeks.greeks.domain.deitytype.DeityTypeRepository;
import br.com.greeks.greeks.exception.NotFoundException;
import br.com.greeks.greeks.service.DeityTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeityTypeServiceTest {

    @Mock
    private DeityTypeRepository deityTypeRepository;

    @InjectMocks
    private DeityTypeService deityTypeService;

    @Test
    void findById_WhenIdExists_ShouldReturnDto() {
        DeityType deityType = new DeityType();
        deityType.setId(1L);
        deityType.setTypeName("GOD");

        when(deityTypeRepository.findById(1L)).thenReturn(Optional.of(deityType));

        var resultDto = deityTypeService.findById(1L);

        assertNotNull(resultDto);
        assertEquals(1L, resultDto.id());
        assertEquals("GOD", resultDto.typeName());
    }

    @Test
    void findById_WhenIdDoesNotExist_ShouldThrowNotFoundException() {
        when(deityTypeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            deityTypeService.findById(99L);
        });
    }
}
