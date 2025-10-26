package com.company.controller;

import com.company.dto.SearchRequest;
import com.company.dto.TierDto;
import com.company.service.TierService;
import com.company.util.CsvExporter;
import com.company.util.ExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    @Autowired
    private TierService tierService;

    @GetMapping
    public ResponseEntity<List<TierDto>> getAllTiers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Note: Basic pagination implementation, could be enhanced
        List<TierDto> tiers = tierService.findAll();
        return ResponseEntity.ok(tiers);
    }

    @GetMapping("/{tiercode}")
    public ResponseEntity<TierDto> getTier(@PathVariable Integer tiercode) {
        TierDto tier = tierService.findById(tiercode);
        if (tier != null) {
            return ResponseEntity.ok(tier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TierDto> createTier(@Valid @RequestBody TierDto tier) {
        try {
            TierDto createdTier = tierService.save(tier);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTier);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{tiercode}")
    public ResponseEntity<TierDto> updateTier(
            @PathVariable Integer tiercode,
            @Valid @RequestBody TierDto tier) {
        try {
            TierDto updatedTier = tierService.update(tiercode, tier);
            if (updatedTier != null) {
                return ResponseEntity.ok(updatedTier);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{tiercode}")
    public ResponseEntity<Void> deleteTier(@PathVariable Integer tiercode) {
        tierService.delete(tiercode);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/search")
    public ResponseEntity<List<TierDto>> searchTiers(@RequestBody SearchRequest searchRequest) {
        List<TierDto> tiers = tierService.searchTiers(
            searchRequest.getSearchTerm(), 
            searchRequest.getFilters()
        );
        return ResponseEntity.ok(tiers);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<TierDto>> searchTiersWithParams(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "tiername") String tiername,
            @RequestParam(required = false, name = "tiercode") Integer tiercode) {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (tiername != null) filters.put("tiername", tiername);
        if (tiercode != null) filters.put("tiercode", tiercode);
        
        List<TierDto> tiers = tierService.searchTiers(searchTerm, filters);
        return ResponseEntity.ok(tiers);
    }
    
    @GetMapping("/export/excel")
    public void exportTiersToExcel(HttpServletResponse response,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, name = "tiername") String tiername,
            @RequestParam(required = false, name = "tiercode") Integer tiercode) throws IOException {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (tiername != null) filters.put("tiername", tiername);
        if (tiercode != null) filters.put("tiercode", tiercode);
        
        List<TierDto> tiers = tierService.searchTiers(searchTerm, filters);
        ExcelExporter.exportTiersToExcel(tiers, response);
    }
    
    @GetMapping("/export/csv")
    public void exportTiersToCsv(HttpServletResponse response,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, name = "tiername") String tiername,
            @RequestParam(required = false, name = "tiercode") Integer tiercode) throws IOException {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (tiername != null) filters.put("tiername", tiername);
        if (tiercode != null) filters.put("tiercode", tiercode);
        
        List<TierDto> tiers = tierService.searchTiers(searchTerm, filters);
        CsvExporter.exportTiersToCsv(tiers, response);
    }
}