package com.alevel.java.ubike.report;

import com.alevel.java.ubike.exception.UbikeReportException;

import java.sql.*;
import java.util.Map;
import java.util.function.Supplier;

public class AmountRidesForRider implements Report<Map<String, Integer>>{
    private final String nickname;
    private final Supplier<Connection> connectionSupplier;

    public AmountRidesForRider(Supplier<Connection> connectionSupplier, String nickname) {
        this.nickname = nickname;
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Map<String, Integer> load() throws UbikeReportException {

        String sql = """
                select count(id) from rides
                where rider_id = (select id from riders where nickname = ?)
                group by rider_id
                """;

        try (PreparedStatement query = connectionSupplier.get().prepareStatement(sql)) {
            query.setString(1, nickname);

            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                int amountOfRidesByNickname = resultSet.getInt(1);

                return Map.of(nickname, amountOfRidesByNickname);
            } else {
                throw new UbikeReportException("No result found");
            }
        } catch (SQLException e) {
            throw new UbikeReportException(e);
        }
    }
}
