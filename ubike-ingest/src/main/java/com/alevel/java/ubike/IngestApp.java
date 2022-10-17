package com.alevel.java.ubike;

import com.alevel.java.ubike.cli.IngestRideInteractiveCLI;
import com.alevel.java.ubike.command.CommandFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IngestApp {

    private static final Logger log = LoggerFactory.getLogger(IngestApp.class);

    public static void main(String[] args) {
        System.exit(new IngestApp().run());
    }

    public int run() {
        try (var serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
             var sessionFactory = new Configuration().buildSessionFactory(serviceRegistry)) {

            var commandFactory = new CommandFactory(sessionFactory);

            var cli = new IngestRideInteractiveCLI(commandFactory);

            cli.run();

        } catch (Exception e) {
            log.error("Error during user interaction", e);
            return -1;
        }
        return 0;
    }

}
