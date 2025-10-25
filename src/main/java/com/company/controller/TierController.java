package com.company.controller;

import com.company.dto.TierDto;
import com.company.service.TierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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
}