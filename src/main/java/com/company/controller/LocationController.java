package com.company.controller;

import com.company.dto.LocationDto;
import com.company.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Note: Basic pagination implementation, could be enhanced
        List<LocationDto> locations = locationService.findAll();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{locationcode}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable String locationcode) {
        LocationDto location = locationService.findById(locationcode);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto location) {
        try {
            LocationDto createdLocation = locationService.save(location);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{locationcode}")
    public ResponseEntity<LocationDto> updateLocation(
            @PathVariable String locationcode,
            @Valid @RequestBody LocationDto location) {
        try {
            LocationDto updatedLocation = locationService.update(locationcode, location);
            if (updatedLocation != null) {
                return ResponseEntity.ok(updatedLocation);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{locationcode}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String locationcode) {
        locationService.delete(locationcode);
        return ResponseEntity.noContent().build();
    }
}