package br.com.greeks.greeks.controller;


import br.com.greeks.greeks.domain.deitytype.CreateDeityTypeDto;
import br.com.greeks.greeks.domain.deitytype.ReadDeityTypeDto;
import br.com.greeks.greeks.domain.deitytype.UpdateDeityTypeDto;
import br.com.greeks.greeks.service.DeityTypeService;
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
@RequestMapping("/api/deity-types")
@Tag(name = "Deity Types", description = "CRUD operations for Deity Types")
public class DeityTypeController {

    @Autowired
    private DeityTypeService deityTypeService;

    @Operation(summary = "Create a new Deity Type")
    @ApiResponse(responseCode = "201", description = "Deity Type created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "Deity Type name already exists")
    @PostMapping
    public ResponseEntity<ReadDeityTypeDto> create(
            @RequestBody @Valid CreateDeityTypeDto dto,
            UriComponentsBuilder uriBuilder) {
        ReadDeityTypeDto created = deityTypeService.create(dto);

        URI uri = uriBuilder.path("/api/deity-types/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(summary = "Get a Deity Type by its ID")
    @ApiResponse(responseCode = "200", description = "Deity Type found")
    @ApiResponse(responseCode = "404", description = "Deity Type not found")
    @GetMapping("/{id}")
    public ResponseEntity<ReadDeityTypeDto> findById(@PathVariable Long id) {
        ReadDeityTypeDto deityType = deityTypeService.findById(id);
        return ResponseEntity.ok(deityType);
    }

    @Operation(summary = "List Deity Types with optional filtering and pagination")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    @GetMapping
    public ResponseEntity<Page<ReadDeityTypeDto>> listFiltered(
            @Parameter(description = "Filter by type name (partial match, case-insensitive)", example = "God")
            @RequestParam(required = false) String typeName,
            @Parameter(description = "Filter by origin (partial match, case-insensitive)", example = "Olympus")
            @RequestParam(required = false) String origin,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"typeName"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ReadDeityTypeDto> page = deityTypeService.findAllFiltered(typeName, origin, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Update an existing Deity Type by ID")
    @ApiResponse(responseCode = "200", description = "Deity Type updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "Deity Type not found")
    @ApiResponse(responseCode = "409", description = "Updated Deity Type name already exists")
    @PutMapping("/{id}")
    public ResponseEntity<ReadDeityTypeDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDeityTypeDto dto) {
        ReadDeityTypeDto updated = deityTypeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a Deity Type by ID")
    @ApiResponse(responseCode = "204", description = "Deity Type deleted successfully")
    @ApiResponse(responseCode = "404", description = "Deity Type not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deityTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}