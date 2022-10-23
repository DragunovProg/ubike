package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.command.CommandFactory;
import com.alevel.java.ubike.command.data.CreateRideRequest;
import com.alevel.java.ubike.command.data.CreateRiderRequest;
import com.alevel.java.ubike.command.data.CreateVehicleRequest;
import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.RideDTO;
import com.alevel.java.ubike.model.dto.RiderDTO;
import com.alevel.java.ubike.model.dto.VehicleDTO;

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

        System.out.println("Hi, select a command\n1. Create a ride\n2. Create a waypoint" +
                "\n3. Create a vehicle\n4. Create a rider");
        int userChoose = scanner.nextInt();

        switch (userChoose) {
            case 1 -> rideInteractionMenu();
            case 2 -> waypointInteractionMenu();
            case 3 -> vehicleInteractionMenu();
            case 4 -> riderInteractionMenu();
            default -> System.out.println("command not found, please try again!");
        }

    }

    private void rideInteractionMenu() throws UbikeIngestException {
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

        RideDTO rideDTO = commandFactory.ingestRide(new CreateRideRequest(
                riderNickname,
                vehicleId,
                finishLocationId,
                startTime,
                finishTime)
        ).execute();

        System.out.printf(
                "Created Ride %d: Vehicle %d moved from (%f;%f) to (%f;%f) starting at %s and finishing at %s%n",
                rideDTO.id(),
                vehicleId,
                rideDTO.start().altitude(),
                rideDTO.start().longitude(),
                rideDTO.finish().altitude(),
                rideDTO.finish().longitude(),
                DateTimeFormatter.ISO_INSTANT.format(rideDTO.startedAt()),
                DateTimeFormatter.ISO_INSTANT.format(rideDTO.finishedAt())
        );

    }

    private void waypointInteractionMenu() throws UbikeIngestException {
        var scanner = new Scanner(System.in);

        System.out.println("Enter altitude:");
        double altitude = scanner.nextDouble();

        System.out.println("Enter longitude:");
        double longitude = scanner.nextDouble();

        Coordinates coordinates = commandFactory.ingestWaypoint(new CreateWaypointRequest(
                altitude,
                longitude)
        ).execute();

        System.out.printf(
                "Created Waypoint (%f,%f)\n",
                coordinates.altitude(),
                coordinates.longitude()

        );
    }

    private void vehicleInteractionMenu() throws UbikeIngestException {
        var scanner = new Scanner(System.in);

        System.out.println("Enter locationId:");
        long locationId = scanner.nextLong();


        VehicleDTO vehicleDTO = commandFactory.ingestVehicle(new CreateVehicleRequest(locationId)).execute();

        System.out.printf(
                "Created Vehicle %d: with location in (%f,%f)\n",
                vehicleDTO.id(),
                vehicleDTO.location().altitude(),
                vehicleDTO.location().longitude()

        );
    }

    private void riderInteractionMenu() throws UbikeIngestException {
        var scanner = new Scanner(System.in);

        System.out.println("Enter nickname:");
        String nickname = scanner.nextLine();

        RiderDTO riderDTO = commandFactory.ingestRider(new CreateRiderRequest(nickname)).execute();
        System.out.printf(
                "Created Rider %d: whose nickname is %s\n",
                riderDTO.id(),
                riderDTO.nickname()

        );

    }
}
