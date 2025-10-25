package com.company.service;

import com.company.dao.LocationDao;
import com.company.dto.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationDao locationDao;

    public List<LocationDto> findAll() {
        return locationDao.findAll();
    }

    public LocationDto findById(String locationcode) {
        return locationDao.findById(locationcode);
    }

    public LocationDto save(LocationDto location) {
        // Validate input
        if (location.getLocationcode() == null || location.getLocationcode().trim().isEmpty()) {
            throw new IllegalArgumentException("Location code is required");
        }
        if (location.getLocationname() == null || location.getLocationname().trim().isEmpty()) {
            throw new IllegalArgumentException("Location name is required");
        }
        if (location.getLocationaddress() == null || location.getLocationaddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Location address is required");
        }
        return locationDao.save(location);
    }

    public LocationDto update(String locationcode, LocationDto location) {
        // Validate input
        if (location.getLocationname() == null || location.getLocationname().trim().isEmpty()) {
            throw new IllegalArgumentException("Location name is required");
        }
        if (location.getLocationaddress() == null || location.getLocationaddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Location address is required");
        }
        return locationDao.update(locationcode, location);
    }

    public void delete(String locationcode) {
        locationDao.delete(locationcode);
    }
}