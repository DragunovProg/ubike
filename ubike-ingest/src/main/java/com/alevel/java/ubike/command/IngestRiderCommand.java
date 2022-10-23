package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateRiderRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Rider;
import com.alevel.java.ubike.model.dto.RideDTO;
import com.alevel.java.ubike.model.dto.RiderDTO;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class IngestRiderCommand implements Command<RiderDTO> {
    private final SessionFactory sessionFactory;
    private final CreateRiderRequest context;

    public IngestRiderCommand(SessionFactory sessionFactory, CreateRiderRequest context) {
        this.sessionFactory = sessionFactory;
        this.context = context;
    }

    @Override
    public RiderDTO execute() throws UbikeIngestException {
        EntityTransaction transaction = null;

        try(var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();


            if (context.nickname() == null) {
                throw new UbikeIngestException("nickname can't be null");
            }

            var rider = new Rider();
            rider.setNickname(context.nickname());

            session.persist(rider);

            var riderDTO = new RiderDTO(rider.getId(), rider.getNickname());

            transaction.commit();



            return riderDTO;
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
