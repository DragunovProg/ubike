package com.alevel.java.ubike.report;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.function.Supplier;

public class ReportFactory {

    private final Supplier<Connection> connectionSupplier;

    public ReportFactory(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public Report<Duration> averageRideDurationForDate(LocalDate date) {
        return new AverageRideDurationForDate(connectionSupplier, date);
    }
}
