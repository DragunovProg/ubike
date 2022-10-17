package com.alevel.java.ubike.config;

import com.alevel.java.ubike.cli.AverageRideDurationForDateCLI;
import com.alevel.java.ubike.cli.InteractiveCLI;
import com.alevel.java.ubike.cli.ReportSelectionCLI;
import com.alevel.java.ubike.report.ReportFactory;

import java.util.Map;

public class CLIConfig {

    private Map<String, InteractiveCLI> reports(ReportFactory reportFactory) {

        return Map.of(
                "avg per day", new AverageRideDurationForDateCLI(reportFactory)
        );
    }

    public InteractiveCLI cli(ReportFactory reportFactory) {
        return new ReportSelectionCLI(reports(reportFactory));
    }



}
