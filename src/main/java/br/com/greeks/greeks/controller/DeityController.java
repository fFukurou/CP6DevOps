package br.com.greeks.greeks.controller;


import br.com.greeks.greeks.domain.deity.CreateDeityDto;
import br.com.greeks.greeks.domain.deity.ReadDeityDto;
import br.com.greeks.greeks.domain.deity.UpdateDeityDto;
import br.com.greeks.greeks.service.DeityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/deities")
@Tag(name = "Deities", description = "CRUD operations for Deities")
public class DeityController {

    @Autowired
    private DeityService deityService;

    @Operation(summary = "Create a new Deity")
    @ApiResponse(responseCode = "201", description = "Deity created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data / Referenced Deity Type not found")
    @ApiResponse(responseCode = "409", description = "Deity name already exists")
    @PostMapping
    public ResponseEntity<ReadDeityDto> create(
            @RequestBody @Valid CreateDeityDto dto,
            UriComponentsBuilder uriBuilder) {
        ReadDeityDto created = deityService.create(dto);
        URI uri = uriBuilder.path("/api/deities/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(summary = "Get a Deity by its ID")
    @ApiResponse(responseCode = "200", description = "Deity found")
    @ApiResponse(responseCode = "404", description = "Deity not found")
    @GetMapping("/{id}")
    public ResponseEntity<ReadDeityDto> findById(@PathVariable Long id) {
        ReadDeityDto deity = deityService.findById(id);
        return ResponseEntity.ok(deity);
    }

    @Operation(summary = "List Deities with optional filtering and pagination")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    @GetMapping
    public ResponseEntity<Page<ReadDeityDto>> listFiltered(
            @Parameter(description = "Filter by name (partial match, case-insensitive)", example = "Zeus")
            @RequestParam(required = false) String name,
            @Parameter(description = "Filter by domain (partial match, case-insensitive)", example = "Sky")
            @RequestParam(required = false) String domain,
            @Parameter(description = "Filter by Deity Type ID", example = "1")
            @RequestParam(required = false) Long deityTypeId,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ReadDeityDto> page = deityService.findAllFiltered(name, domain, deityTypeId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Update an existing Deity by ID")
    @ApiResponse(responseCode = "200", description = "Deity updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data / Referenced Deity Type not found")
    @ApiResponse(responseCode = "404", description = "Deity not found")
    @ApiResponse(responseCode = "409", description = "Updated Deity name already exists")
    @PutMapping("/{id}")
    public ResponseEntity<ReadDeityDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDeityDto dto) {
        ReadDeityDto updated = deityService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a Deity by ID")
    @ApiResponse(responseCode = "204", description = "Deity deleted successfully")
    @ApiResponse(responseCode = "404", description = "Deity not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}