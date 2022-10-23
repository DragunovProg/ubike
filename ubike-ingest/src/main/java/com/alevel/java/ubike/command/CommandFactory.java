package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateRideRequest;
import com.alevel.java.ubike.command.data.CreateRiderRequest;
import com.alevel.java.ubike.command.data.CreateVehicleRequest;
import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.RideDTO;
import com.alevel.java.ubike.model.dto.RiderDTO;
import com.alevel.java.ubike.model.dto.VehicleDTO;
import org.hibernate.SessionFactory;

public class CommandFactory {

    private final SessionFactory sessionFactory;

    public CommandFactory(SessionFactory session) {
        this.sessionFactory = session;
    }

    public Command<RideDTO> ingestRide(CreateRideRequest context) {
        return new IngestRideCommand(sessionFactory, context);
    }

    public Command<Coordinates> ingestWaypoint(CreateWaypointRequest context) {
        return new IngestWaypointCommand(sessionFactory, context);
    }

    public Command<VehicleDTO> ingestVehicle(CreateVehicleRequest context) {
        return new IngestVehicleCommand(sessionFactory, context);
    }

    public Command<RiderDTO> ingestRider(CreateRiderRequest context) {
        return new IngestRiderCommand(sessionFactory, context);
    }
}
