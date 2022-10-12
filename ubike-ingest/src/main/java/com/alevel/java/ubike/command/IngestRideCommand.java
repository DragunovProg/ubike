package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateRideRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Ride;
import com.alevel.java.ubike.model.Rider;
import com.alevel.java.ubike.model.Vehicle;
import com.alevel.java.ubike.model.Waypoint;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.RideDTO;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

class IngestRideCommand implements Command<RideDTO> {

    private final SessionFactory sessionFactory;

    private final CreateRideRequest context;

    IngestRideCommand(SessionFactory sessionFactory, CreateRideRequest context) {
        this.sessionFactory = sessionFactory;
        this.context = context;
    }

    @Override
    public RideDTO execute() throws UbikeIngestException {

        EntityTransaction tx = null;

        try (var session = sessionFactory.openSession()) {

            tx = session.beginTransaction();

            Rider rider = session.bySimpleNaturalId(Rider.class)
                    .loadOptional(context.nickname())
                    .orElseThrow(() -> new UbikeIngestException("No rider found by nickname " + context.nickname()));


            Vehicle vehicle = session.find(Vehicle.class, context.vehicleId());

            if (vehicle == null) {
                throw new UbikeIngestException("No vehicle found by id " + context.vehicleId());

            }

            Waypoint start = vehicle.getLocation();

            Waypoint finish = session.find(Waypoint.class, context.finishId());

            if (finish == null) {
                throw new UbikeIngestException("No waypoint found by id " + context.finishId());
            }

            var ride = new Ride();
            ride.setRider(rider);
            ride.setVehicle(vehicle);
            ride.setStart(start);
            ride.setFinish(finish);
            ride.setStartedAt(context.startedAt());
            ride.setFinishedAt(context.finishedAt());

            session.persist(ride);

            vehicle.setLocation(finish);

            var result = new RideDTO(
                    ride.getId(),
                    rider.getNickname(),
                    vehicle.getId(),
                    new Coordinates(start.getAltitude(), start.getLongitude()),
                    new Coordinates(finish.getAltitude(), finish.getLongitude()),
                    context.startedAt(),
                    context.finishedAt()
            );

            tx.commit();

            return result;

        } catch (UbikeIngestException e) {
            tx.rollback();
            throw e;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new UbikeIngestException(e);
        }
    }

}
