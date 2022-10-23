package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Waypoint;
import com.alevel.java.ubike.model.dto.Coordinates;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class IngestWaypointCommand implements Command<Coordinates>{
    private final SessionFactory sessionFactory;
    private final CreateWaypointRequest context;

    public IngestWaypointCommand(SessionFactory sessionFactory, CreateWaypointRequest context) {
        this.sessionFactory = sessionFactory;
        this.context = context;
    }

    @Override
    public Coordinates execute() throws UbikeIngestException {
        EntityTransaction transaction = null;

        try(var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            if (context.longitude() == 0.0 || context.altitude() == 0.0) {
                throw new UbikeIngestException("coordinates isn't valid");
            }

            Waypoint waypoint = new Waypoint();
            waypoint.setAltitude(context.altitude());
            waypoint.setLongitude(context.longitude());

            session.persist(waypoint);

            Coordinates coordinates = new Coordinates(context.altitude(), context.longitude());
            sessionFactory.close();

            transaction.commit();


            return coordinates;
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
