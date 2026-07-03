package com.tourism.app.service;

import com.tourism.app.model.Attraction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AttractionService {

    private final List<Attraction> attractions = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public AttractionService() {
        attractions.add(new Attraction(idCounter.getAndIncrement(),
                "Silver Falls Viewpoint", "Northern Hills",
                "Nature",
                "A dramatic cascading waterfall surrounded by pine forests, best visited at sunrise.",
                "images/silver-falls.jpg"));
        attractions.add(new Attraction(idCounter.getAndIncrement(),
                "Old Fort Heritage Museum", "Capital City",
                "Heritage",
                "A 400-year-old fort turned museum, showcasing the state's royal history and artifacts.",
                "images/old-fort.jpg"));
        attractions.add(new Attraction(idCounter.getAndIncrement(),
                "Golden Sands Beach", "Coastal District",
                "Beach",
                "A long stretch of golden-sand coastline popular for sunset walks and water sports.",
                "images/golden-sands.jpg"));
        attractions.add(new Attraction(idCounter.getAndIncrement(),
                "Emerald Valley Trek", "Western Ghats",
                "Adventure",
                "A scenic two-day trekking trail through tea estates and cloud forests.",
                "images/emerald-valley.jpg"));
        attractions.add(new Attraction(idCounter.getAndIncrement(),
                "Lakeview Botanical Garden", "Capital City",
                "Nature",
                "A sprawling botanical garden with a boating lake and seasonal flower exhibitions.",
                "images/lakeview-garden.jpg"));
    }

    public List<Attraction> getAll() {
        return attractions;
    }

    public Attraction add(Attraction attraction) {
        attraction.setId(idCounter.getAndIncrement());
        attractions.add(attraction);
        return attraction;
    }
}
