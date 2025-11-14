package org.ecoride.tripservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecoride.tripservice.model.enums.TripStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripResponse {

    private UUID id;
    private UUID driverId;
    private String origin;
    private String destination;
    private LocalDateTime startTime;
    private Integer seatsTotal;
    private Integer seatsAvailable;
    private BigDecimal price;
    private TripStatus status;
    private LocalDateTime createdAt;
}