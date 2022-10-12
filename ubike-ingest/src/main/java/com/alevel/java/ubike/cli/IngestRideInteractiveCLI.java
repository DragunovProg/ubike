package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.command.Command;
import com.alevel.java.ubike.command.CommandFactory;
import com.alevel.java.ubike.command.data.CreateRideRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.dto.RideDTO;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class IngestRideInteractiveCLI {

    private final CommandFactory commandFactory;


    public IngestRideInteractiveCLI(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }


    public void run() throws UbikeIngestException {

        var scanner = new Scanner(System.in);

        System.out.println("Enter rider nickname:");
        String riderNickname = scanner.nextLine();

        System.out.println("Enter vehicle id:");
        long vehicleId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter finish location id:");
        long finishLocationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter ride start time:");
        Instant startTime = Instant.parse(scanner.nextLine());

        System.out.println("Enter ride duration minutes:");
        Instant finishTime = startTime.plus(Duration.ofMinutes(scanner.nextLong()));


        Command<RideDTO> command = commandFactory.ingestRide(new CreateRideRequest(
                riderNickname,
                vehicleId,
                finishLocationId,
                startTime,
                finishTime
        ));

        RideDTO ride = command.execute();

        System.out.printf(
                "Created Ride %d: Vehicle %d moved from (%f;%f) to (%f;%f) starting at %s and finishing at %s%n",
                ride.id(),
                vehicleId,
                ride.start().altitude(),
                ride.start().longitude(),
                ride.finish().altitude(),
                ride.finish().longitude(),
                DateTimeFormatter.ISO_INSTANT.format(ride.startedAt()),
                DateTimeFormatter.ISO_INSTANT.format(ride.finishedAt())
        );

    }


}
