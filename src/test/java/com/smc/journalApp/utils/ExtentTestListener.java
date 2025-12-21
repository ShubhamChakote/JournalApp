package com.smc.journalApp.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class ExtentTestListener implements ConcurrentEventListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> scenario = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(TestCaseStarted.class, this::onScenarioStart);
        publisher.registerHandlerFor(TestStepFinished.class, this::onStepFinish);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onScenarioFinish);
    }

    private void onScenarioStart(TestCaseStarted event) {
        ExtentTest test = extent.createTest(event.getTestCase().getName());
        scenario.set(test);
    }

    private void onStepFinish(TestStepFinished event) {

        if (!(event.getTestStep() instanceof PickleStepTestStep)) return;

        String stepText =
                ((PickleStepTestStep) event.getTestStep()).getStep().getText();

        switch (event.getResult().getStatus()) {
            case PASSED:
                scenario.get().pass(stepText);
                break;
            case FAILED:
                scenario.get().fail(stepText)
                        .fail(event.getResult().getError());
                break;
            case SKIPPED:
                scenario.get().skip(stepText);
                break;
        }
    }

    private void onScenarioFinish(TestCaseFinished event) {
        extent.flush();
    }
}
