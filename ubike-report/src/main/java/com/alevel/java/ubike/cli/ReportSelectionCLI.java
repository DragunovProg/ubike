package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.exception.UbikeReportException;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReportSelectionCLI implements InteractiveCLI {

    private final Map<String, InteractiveCLI> selectableReportCLIs;

    public ReportSelectionCLI(Map<String, InteractiveCLI> selectableReportCLIs) {
        this.selectableReportCLIs = selectableReportCLIs;
    }

    @Override
    public void run() throws UbikeReportException {

        String menu = selectableReportCLIs.keySet().stream()
                .map(item -> "- " + item)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.printf("Available reports:%n%s%n", menu);

        InteractiveCLI option;
        var scanner = new Scanner(System.in);
        do {
            System.out.println("Please, enter the name of the report");
            option = selectableReportCLIs.get(scanner.nextLine());
        } while (option == null);

        option.run();
    }
}
