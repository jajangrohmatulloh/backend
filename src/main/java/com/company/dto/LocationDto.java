package com.company.dto;

public class LocationDto {
    private String locationcode;
    private String locationname;
    private String locationaddress;

    public LocationDto() {}

    public LocationDto(String locationcode, String locationname, String locationaddress) {
        this.locationcode = locationcode;
        this.locationname = locationname;
        this.locationaddress = locationaddress;
    }

    // Getters and Setters
    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getLocationaddress() {
        return locationaddress;
    }

    public void setLocationaddress(String locationaddress) {
        this.locationaddress = locationaddress;
    }
}