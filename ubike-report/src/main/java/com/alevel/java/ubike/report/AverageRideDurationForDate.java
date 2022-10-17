package com.alevel.java.ubike.report;

import com.alevel.java.ubike.exception.UbikeReportException;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.function.Supplier;

public class AverageRideDurationForDate implements Report<Duration> {

    private final LocalDate date;

    private final Supplier<Connection> connectionSupplier;


    public AverageRideDurationForDate(Supplier<Connection> connectionProvider, LocalDate date) {
        this.date = date;
        this.connectionSupplier = connectionProvider;
    }

    @Override
    public Duration load() throws UbikeReportException {

        Timestamp start = Timestamp.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));

        Timestamp finish = Timestamp.from(date.atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC));

        String sql = """
                select avg(extract(epoch from r.finished_at - r.started_at)) as result
                from rides r where r.started_at >= ? and r.finished_at <= ?
                """;

        try (PreparedStatement query = connectionSupplier.get().prepareStatement(sql)) {
            query.setTimestamp(1, start);
            query.setTimestamp(2, finish);

            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                double averageSeconds = resultSet.getDouble(1);
                long roundedToSeconds = Math.round(averageSeconds);
                return Duration.ofSeconds(roundedToSeconds);
            } else {
                throw new UbikeReportException("No result found");
            }
        } catch (SQLException e) {
            throw new UbikeReportException(e);
        }
    }
}
