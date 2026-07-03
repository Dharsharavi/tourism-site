package com.tourism.app.service;

import com.tourism.app.model.Booking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookingService {

    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Booking save(Booking booking) {
        booking.setId(idCounter.getAndIncrement());
        bookings.add(booking);
        return booking;
    }

    public List<Booking> getAll() {
        return bookings;
    }

    public int count() {
        return bookings.size();
    }
}
