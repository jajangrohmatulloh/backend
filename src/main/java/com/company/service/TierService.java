package com.company.service;

import com.company.dao.TierDao;
import com.company.dto.TierDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierService {

    @Autowired
    private TierDao tierDao;

    public List<TierDto> findAll() {
        return tierDao.findAll();
    }

    public TierDto findById(Integer tiercode) {
        return tierDao.findById(tiercode);
    }

    public TierDto save(TierDto tier) {
        // Validate input
        if (tier.getTiercode() == null) {
            throw new IllegalArgumentException("Tier code is required");
        }
        if (tier.getTiername() == null || tier.getTiername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tier name is required");
        }
        return tierDao.save(tier);
    }

    public TierDto update(Integer tiercode, TierDto tier) {
        // Validate input
        if (tier.getTiername() == null || tier.getTiername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tier name is required");
        }
        return tierDao.update(tiercode, tier);
    }

    public void delete(Integer tiercode) {
        tierDao.delete(tiercode);
    }
}