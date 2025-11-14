package org.ecoride.tripservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecoride.tripservice.dto.CreateTripRequest;
import org.ecoride.tripservice.dto.ReservationResponse;
import org.ecoride.tripservice.dto.TripResponse;
import org.ecoride.tripservice.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Trips", description = "Trip management API")
public class TripController {

    private final TripService tripService;

    @PostMapping
    @Operation(summary = "Create a new trip (ROLE_DRIVER required)")
    public ResponseEntity<TripResponse> createTrip(
            @Valid @RequestBody CreateTripRequest request,
            @RequestHeader("X-User-Id") UUID driverId) {

        log.info("POST /trips - Creating trip for driver: {}", driverId);
        TripResponse response = tripService.createTrip(request, driverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Search trips")
    public ResponseEntity<List<TripResponse>> searchTrips(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        log.info("GET /trips - Searching trips");
        List<TripResponse> trips = tripService.searchTrips(origin, destination, from, to);
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{tripId}")
    @Operation(summary = "Get trip by ID")
    public ResponseEntity<TripResponse> getTripById(@PathVariable UUID tripId) {
        log.info("GET /trips/{} - Getting trip details", tripId);
        TripResponse trip = tripService.getTripById(tripId);
        return ResponseEntity.ok(trip);
    }

    @PostMapping("/{tripId}/reservations")
    @Operation(summary = "Create a reservation (ROLE_PASSENGER required)")
    public ResponseEntity<ReservationResponse> createReservation(
            @PathVariable UUID tripId,
            @RequestHeader("X-User-Id") UUID passengerId) {

        log.info("POST /trips/{}/reservations - Creating reservation for passenger: {}",
                tripId, passengerId);
        ReservationResponse response = tripService.createReservation(tripId, passengerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/reservations/{reservationId}")
    @Operation(summary = "Get reservation by ID")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable UUID reservationId) {
        log.info("GET /reservations/{} - Getting reservation details", reservationId);
        ReservationResponse reservation = tripService.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }
}