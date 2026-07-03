package com.tourism.app.controller;

import com.tourism.app.model.Attraction;
import com.tourism.app.service.AttractionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public List<Attraction> getAttractions() {
        return attractionService.getAll();
    }

    @PostMapping
    public Attraction addAttraction(@RequestBody Attraction attraction) {
        return attractionService.add(attraction);
    }
}
