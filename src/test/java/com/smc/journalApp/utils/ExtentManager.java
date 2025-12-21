package com.smc.journalApp.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {
            ExtentSparkReporter spark =
                    new ExtentSparkReporter("target/extent-report/ExtentReport.html");

            spark.config().setReportName("Journal App API Automation");
            spark.config().setDocumentTitle("API Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Project", "Journal App");
            extent.setSystemInfo("Tester", "Shubham Chakote");
            extent.setSystemInfo("Framework", "Cucumber + RestAssured");
        }
        return extent;
    }
}
