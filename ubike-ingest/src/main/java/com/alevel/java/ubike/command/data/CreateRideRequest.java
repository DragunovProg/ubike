package com.alevel.java.ubike.command.data;

import java.time.Instant;

public record CreateRideRequest(
        String nickname,
        Long vehicleId,
        Long finishId,
        Instant startedAt,
        Instant finishedAt
) {

}
