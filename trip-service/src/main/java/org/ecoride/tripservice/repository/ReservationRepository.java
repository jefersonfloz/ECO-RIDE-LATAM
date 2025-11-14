package org.ecoride.tripservice.repository;

import org.ecoride.tripservice.model.entity.Reservation;
import org.ecoride.tripservice.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByPassengerIdAndStatus(UUID passengerId, ReservationStatus status);

    List<Reservation> findByPassengerId(UUID passengerId);

    @Query("SELECT r FROM Reservation r WHERE r.trip.id = :tripId AND r.passengerId = :passengerId")
    Optional<Reservation> findByTripAndPassenger(
            @Param("tripId") UUID tripId,
            @Param("passengerId") UUID passengerId
    );

    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.trip.id = :tripId " +
            "AND r.passengerId = :passengerId " +
            "AND r.status IN ('PENDING', 'CONFIRMED')")
    boolean existsActiveReservation(
            @Param("tripId") UUID tripId,
            @Param("passengerId") UUID passengerId
    );
}