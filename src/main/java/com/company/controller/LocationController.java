package com.company.controller;

import com.company.dto.LocationDto;
import com.company.dto.SearchRequest;
import com.company.service.LocationService;
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
    
    @PostMapping("/search")
    public ResponseEntity<List<LocationDto>> searchLocations(@RequestBody SearchRequest searchRequest) {
        List<LocationDto> locations = locationService.searchLocations(
            searchRequest.getSearchTerm(), 
            searchRequest.getFilters()
        );
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<LocationDto>> searchLocationsWithParams(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "locationname") String locationname,
            @RequestParam(required = false, name = "locationaddress") String locationaddress,
            @RequestParam(required = false, name = "locationcode") String locationcode) {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (locationname != null) filters.put("locationname", locationname);
        if (locationaddress != null) filters.put("locationaddress", locationaddress);
        if (locationcode != null) filters.put("locationcode", locationcode);
        
        List<LocationDto> locations = locationService.searchLocations(searchTerm, filters);
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/export/excel")
    public void exportLocationsToExcel(HttpServletResponse response,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, name = "locationname") String locationname,
            @RequestParam(required = false, name = "locationaddress") String locationaddress,
            @RequestParam(required = false, name = "locationcode") String locationcode) throws IOException {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (locationname != null) filters.put("locationname", locationname);
        if (locationaddress != null) filters.put("locationaddress", locationaddress);
        if (locationcode != null) filters.put("locationcode", locationcode);
        
        List<LocationDto> locations = locationService.searchLocations(searchTerm, filters);
        ExcelExporter.exportLocationsToExcel(locations, response);
    }
    
    @GetMapping("/export/csv")
    public void exportLocationsToCsv(HttpServletResponse response,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false, name = "locationname") String locationname,
            @RequestParam(required = false, name = "locationaddress") String locationaddress,
            @RequestParam(required = false, name = "locationcode") String locationcode) throws IOException {
        
        // Build filters map
        Map<String, Object> filters = new java.util.HashMap<>();
        if (locationname != null) filters.put("locationname", locationname);
        if (locationaddress != null) filters.put("locationaddress", locationaddress);
        if (locationcode != null) filters.put("locationcode", locationcode);
        
        List<LocationDto> locations = locationService.searchLocations(searchTerm, filters);
        CsvExporter.exportLocationsToCsv(locations, response);
    }
}