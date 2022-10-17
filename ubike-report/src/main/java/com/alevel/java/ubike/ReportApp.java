package com.alevel.java.ubike;

import com.alevel.java.ubike.config.CLIConfig;
import com.alevel.java.ubike.config.ConnectionProvider;
import com.alevel.java.ubike.config.PropertiesConfig;
import com.alevel.java.ubike.report.ReportFactory;

import java.sql.SQLException;

public class ReportApp {

    public static void main(String[] args) {
        System.exit(new ReportApp().run());
    }

    public int run() {
        var jdbcProps = new PropertiesConfig().jdbcProperties();

        try (var connectionProvider = new ConnectionProvider(jdbcProps)) {

            var reportFactory = new ReportFactory(connectionProvider);

            var cli = new CLIConfig().cli(reportFactory);

            cli.run();

        } catch (SQLException e) {
            System.err.println("Error during database operation: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return -1;
        }

        return 0;
    }

}
