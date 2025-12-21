package com.smc.journalApp.context;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    public static Map<String, Object> context = new HashMap<>();

    private TestContext() {
        // prevent instantiation
    }
}
