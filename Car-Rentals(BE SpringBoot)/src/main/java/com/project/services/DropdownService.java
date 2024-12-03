package com.project.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.project.repositories.*;

@Service
public class DropdownService {

    @Autowired
    private CarRepository carRepository;

    public Map<String, List<String>> getDropdownData() {
        Map<String, List<String>> dropdownData = new HashMap<>();
        dropdownData.put("brands", carRepository.findDistinctBrands());
        dropdownData.put("types", carRepository.findDistinctTypes());
        dropdownData.put("colors", carRepository.findDistinctColors());
        dropdownData.put("transmissions", carRepository.findDistinctTransmissions());
        return dropdownData;
    }
}

