package org.ecoride.tripservice.repository;

import org.ecoride.tripservice.model.entity.Trip;
import org.ecoride.tripservice.model.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {

    List<Trip> findByDriverIdAndStatus(UUID driverId, TripStatus status);

    @Query("SELECT t FROM Trip t WHERE t.status = :status " +
            "AND (:origin IS NULL OR LOWER(t.origin) LIKE LOWER(CONCAT('%', :origin, '%'))) " +
            "AND (:destination IS NULL OR LOWER(t.destination) LIKE LOWER(CONCAT('%', :destination, '%'))) " +
            "AND (:from IS NULL OR t.startTime >= :from) " +
            "AND (:to IS NULL OR t.startTime <= :to) " +
            "AND t.seatsAvailable > 0 " +
            "ORDER BY t.startTime ASC")
    List<Trip> searchTrips(
            @Param("status") TripStatus status,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT t FROM Trip t WHERE t.driverId = :driverId " +
            "AND t.startTime BETWEEN :start AND :end")
    List<Trip> findDriverTripsInRange(
            @Param("driverId") UUID driverId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}