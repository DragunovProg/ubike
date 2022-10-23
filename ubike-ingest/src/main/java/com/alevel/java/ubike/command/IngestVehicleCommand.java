package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateVehicleRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Vehicle;
import com.alevel.java.ubike.model.Waypoint;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.VehicleDTO;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class IngestVehicleCommand implements Command<VehicleDTO>{
    private final SessionFactory sessionFactory;
    private final CreateVehicleRequest context;

    public IngestVehicleCommand(SessionFactory sessionFactory, CreateVehicleRequest context) {
        this.sessionFactory = sessionFactory;
        this.context = context;
    }

    @Override
    public VehicleDTO execute() throws UbikeIngestException {
        EntityTransaction transaction = null;

        try(var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Waypoint waypoint = session.find(Waypoint.class, context.locationId());

            if (waypoint == null) {
                throw new UbikeIngestException("location can't be null");
            }

            var vehicle = new Vehicle();
            vehicle.setLocation(waypoint);

            session.persist(vehicle);

            VehicleDTO vehicleDTO = new VehicleDTO(
                    vehicle.getId(),
                    new Coordinates(waypoint.getAltitude(), waypoint.getLongitude())
            );

            transaction.commit();



            return vehicleDTO;
        } catch (UbikeIngestException e) {
            transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new UbikeIngestException(e);
        }
    }
}
