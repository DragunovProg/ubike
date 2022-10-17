package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.exception.UbikeReportException;
import com.alevel.java.ubike.report.ReportFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AverageRideDurationForDateCLI implements InteractiveCLI {

    private final ReportFactory reportFactory;

    public AverageRideDurationForDateCLI(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    @Override
    public void run() throws UbikeReportException {
        var scanner = new Scanner(System.in);

        System.out.println("Please, enter a date like '2011-12-03'");

        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

        Duration result = reportFactory.averageRideDurationForDate(date).load();

        System.out.printf("Average duration on that date is: %d minutes, %d seconds%n", result.toMinutes(), result.toSecondsPart());
    }
}
