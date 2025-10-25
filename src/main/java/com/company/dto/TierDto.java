package com.company.dto;

public class TierDto {
    private Integer tiercode;
    private String tiername;

    public TierDto() {}

    public TierDto(Integer tiercode, String tiername) {
        this.tiercode = tiercode;
        this.tiername = tiername;
    }

    // Getters and Setters
    public Integer getTiercode() {
        return tiercode;
    }

    public void setTiercode(Integer tiercode) {
        this.tiercode = tiercode;
    }

    public String getTiername() {
        return tiername;
    }

    public void setTiername(String tiername) {
        this.tiername = tiername;
    }
}