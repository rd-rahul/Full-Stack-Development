package com.project.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.project.repositories.CarRepository;

import java.util.*;

@RestController
@RequestMapping("/api")
public class DropdownController {

    private final CarRepository carRepository;

    public DropdownController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/dropdown-data")
    public ResponseEntity<Map<String, List<String>>> getDropdownData() {
        Map<String, List<String>> dropdownData = new HashMap<>();
        
        dropdownData.put("brands", carRepository.findDistinctBrands());
        dropdownData.put("types", carRepository.findDistinctTypes());
        dropdownData.put("colors", carRepository.findDistinctColors());
        dropdownData.put("transmissions", carRepository.findDistinctTransmissions());
        
        return ResponseEntity.ok(dropdownData);
    }

    @GetMapping("/filtered-dropdown-data")
    public ResponseEntity<Map<String, List<String>>> getFilteredDropdownData(
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String color
    ) {
        Map<String, List<String>> filteredData = new HashMap<>();

        if (brand != null && type != null && color != null) {
            filteredData.put("transmissions", carRepository.findDistinctTransmissionsByBrandAndTypeAndColor(brand, type, color));
        } else if (brand != null && type != null) {
            filteredData.put("colors", carRepository.findDistinctColorsByBrandAndType(brand, type));
            filteredData.put("transmissions", carRepository.findDistinctTransmissionsByBrandAndType(brand, type));
        } else if (type != null && color != null) {
            filteredData.put("transmissions", carRepository.findDistinctTransmissionsByTypeAndColor(type, color));
        } else if (brand != null) {
            filteredData.put("types", carRepository.findDistinctTypesByBrand(brand));
            filteredData.put("colors", carRepository.findDistinctColorsByBrand(brand));
            filteredData.put("transmissions", carRepository.findDistinctTransmissionsByBrand(brand));
        } else if (type != null) {
            filteredData.put("colors", carRepository.findDistinctColorsByType(type));
            filteredData.put("transmissions", carRepository.findDistinctTransmissionsByType(type));
        } else if (color != null) {
            filteredData.put("transmissions", carRepository.findDistinctTransmissionsByColor(color));
        }

        return ResponseEntity.ok(filteredData);
    }
}
