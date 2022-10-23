package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.exception.UbikeReportException;
import com.alevel.java.ubike.report.ReportFactory;

import java.util.Map;
import java.util.Scanner;

public class AmountOfVehiclesUsedByGivenRider implements InteractiveCLI{
    private final ReportFactory reportFactory;

    public AmountOfVehiclesUsedByGivenRider(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    @Override
    public void run() throws UbikeReportException {
        var scanner = new Scanner(System.in);

        System.out.println("Please, enter a rider nickname:");

        String nickname = scanner.nextLine();

        Map<String, Integer> result = reportFactory.amountOfVehiclesUsedByGivenRider(nickname).load();

        System.out.printf("Rider %s rides on %d vehicles%n", nickname, result.get(nickname));
    }
}
