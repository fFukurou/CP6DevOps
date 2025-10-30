package br.com.greeks.greeks;

import br.com.greeks.greeks.domain.deity.DeityRepository; // IMPORTAR DEITY REPOSITORY
import br.com.greeks.greeks.domain.deitytype.CreateDeityTypeDto;
import br.com.greeks.greeks.domain.deitytype.DeityType;
import br.com.greeks.greeks.domain.deitytype.DeityTypeRepository;
import br.com.greeks.greeks.domain.deitytype.UpdateDeityTypeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional; // Importar jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class DeityTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeityTypeRepository deityTypeRepository;

    @Autowired
    private DeityRepository deityRepository;


    @BeforeEach
    @Transactional
    void setUp() {

        List<String> testTypeNames = List.of("Test Hero", "Test Hero (Updated)", "Existing Type");

        List<DeityType> testTypes = deityTypeRepository.findAll().stream()
                .filter(dt -> dt.getTypeName() != null && testTypeNames.contains(dt.getTypeName()))
                .toList();

        if (!testTypes.isEmpty()) {
            List<Long> testTypeIds = testTypes.stream().map(DeityType::getId).toList();
            deityRepository.deleteAll(
                    deityRepository.findAll().stream()
                            .filter(d -> d.getDeityType() != null && testTypeIds.contains(d.getDeityType().getId()))
                            .toList()
            );
        }

        if (!testTypes.isEmpty()) {
            deityTypeRepository.deleteAll(testTypes);
        }

        deityTypeRepository.findByTypeNameIgnoreCase("Existing Type").ifPresent(deityTypeRepository::delete);
        deityTypeRepository.findByTypeNameIgnoreCase("Test Hero").ifPresent(deityTypeRepository::delete);
        deityTypeRepository.findByTypeNameIgnoreCase("Test Hero (Updated)").ifPresent(deityTypeRepository::delete);
    }

    private DeityType createTestDeityType(String name) {
        DeityType dt = new DeityType();
        dt.setTypeName(name);
        dt.setOrigin("Test Origin");
        dt.setLifespan("Test Lifespan");
        dt.setDescription("Test Description");
        dt.setPowerSource("Test Power");
        return deityTypeRepository.saveAndFlush(dt);
    }


    @Test
    void testDeityTypeCrudLifecycle() throws Exception {
        CreateDeityTypeDto createDto = new CreateDeityTypeDto(
                "Test Hero",
                "Born Mortal, Ascended",
                "Heroic mortals who achieved divinity.",
                "Immortal (Post-Ascension)",
                "Heroic Deeds"
        );

        MvcResult createResult = mockMvc.perform(post("/api/deity-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.typeName", is("Test Hero")))
                .andExpect(jsonPath("$.origin", is("Born Mortal, Ascended")))
                .andReturn();

        String responseBody = createResult.getResponse().getContentAsString();
        Long createdId = objectMapper.readTree(responseBody).get("id").asLong();

        mockMvc.perform(get("/api/deity-types/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdId.intValue())))
                .andExpect(jsonPath("$.typeName", is("Test Hero")));

        UpdateDeityTypeDto updateDto = new UpdateDeityTypeDto(
                "Test Hero (Updated)",
                null,
                "Updated Description",
                null,
                null
        );

        mockMvc.perform(put("/api/deity-types/" + createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdId.intValue())))
                .andExpect(jsonPath("$.typeName", is("Test Hero (Updated)")))
                .andExpect(jsonPath("$.description", is("Updated Description")));

        mockMvc.perform(delete("/api/deity-types/" + createdId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/deity-types/" + createdId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDeityType_WhenNameIsDuplicate_ShouldReturnConflict() throws Exception {
        createTestDeityType("Existing Type");

        CreateDeityTypeDto createDto = new CreateDeityTypeDto(
                "Existing Type",
                "Test Origin",
                "Test Description",
                "Test Lifespan",
                "Test Power"
        );

        mockMvc.perform(post("/api/deity-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void getDeityType_WhenIdDoesNotExist_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/deity-types/99999"))
                .andExpect(status().isNotFound());
    }
}