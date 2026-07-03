package com.tourism.app.controller;

import com.tourism.app.model.Booking;
import com.tourism.app.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(@Valid @RequestBody Booking booking) {
        return bookingService.save(booking);
    }

    @GetMapping("/count")
    public Map<String, Integer> count() {
        Map<String, Integer> body = new HashMap<>();
        body.put("totalBookings", bookingService.count());
        return body;
    }
}
