package com.alevel.java.ubike.report;

import com.alevel.java.ubike.exception.UbikeReportException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

public class AmountOfVehicleUsedByGivenRider implements Report<Map<String, Integer>> {
        private final String nickname;
        private final Supplier<Connection> connectionSupplier;

    public AmountOfVehicleUsedByGivenRider(Supplier<Connection> connectionSupplier, String nickname) {
        this.nickname = nickname;
        this.connectionSupplier = connectionSupplier;
    }


    @Override
    public Map<String, Integer> load() throws UbikeReportException {

        String sql = """
                select r.nickname, count(DISTINCT rs.vehicle_id)  from rides rs
                    join riders r on  r.id = rs.rider_id
                where  r.nickname = ?
                group by r.nickname
                """;

        try (PreparedStatement query = connectionSupplier.get().prepareStatement(sql)) {
            query.setString(1, nickname);

            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                int vehicles = resultSet.getInt(1);

                return Map.of(nickname, vehicles);
            } else {
                throw new UbikeReportException("No result found");
            }
        } catch (SQLException e) {
            throw new UbikeReportException(e);
        }
    }
}
