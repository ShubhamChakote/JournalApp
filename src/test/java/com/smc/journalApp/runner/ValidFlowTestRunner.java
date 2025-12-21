package com.smc.journalApp.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.smc.journalApp"
        },
        plugin = {
                "pretty",
                "com.smc.journalApp.utils.ExtentTestListener"
        },
        tags = "@basicflow",
        monochrome = true,
        publish = true
)
public class ValidFlowTestRunner {
}
