package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.exception.UbikeReportException;
import com.alevel.java.ubike.report.ReportFactory;


import java.util.Map;
import java.util.Scanner;

public class AmountRidesForRiderCLI implements InteractiveCLI{
    private final ReportFactory reportFactory;

    public AmountRidesForRiderCLI(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    @Override
    public void run() throws UbikeReportException {
        var scanner = new Scanner(System.in);

        System.out.println("Please, enter a rider nickname:");

        String nickname = scanner.nextLine();

        Map<String, Integer> result = reportFactory.amountRidesForRider(nickname).load();

        System.out.printf("Rider %s have a %d rides%n", nickname, result.get(nickname));
    }
}

