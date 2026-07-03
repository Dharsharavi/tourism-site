package com.tourism.app.service;

import com.tourism.app.model.Hotel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HotelService {

    private final List<Hotel> hotels = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public HotelService() {
        hotels.add(new Hotel(idCounter.getAndIncrement(),
                "Grand Capital Palace", "Capital City", 5, 8500.00, "+91-90000-10001"));
        hotels.add(new Hotel(idCounter.getAndIncrement(),
                "Seabreeze Resort & Spa", "Coastal District", 4, 5200.00, "+91-90000-10002"));
        hotels.add(new Hotel(idCounter.getAndIncrement(),
                "Hillcrest Homestay", "Northern Hills", 3, 2400.00, "+91-90000-10003"));
        hotels.add(new Hotel(idCounter.getAndIncrement(),
                "Emerald Tea Cottage", "Western Ghats", 3, 2800.00, "+91-90000-10004"));
        hotels.add(new Hotel(idCounter.getAndIncrement(),
                "Lakeview Boutique Inn", "Capital City", 4, 4600.00, "+91-90000-10005"));
    }

    public List<Hotel> getAll() {
        return hotels;
    }

    public Hotel add(Hotel hotel) {
        hotel.setId(idCounter.getAndIncrement());
        hotels.add(hotel);
        return hotel;
    }
}
