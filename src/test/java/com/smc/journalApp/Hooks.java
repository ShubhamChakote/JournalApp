package com.smc.journalApp;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import static com.smc.journalApp.context.TestContext.context;
import static io.restassured.RestAssured.given;

public class Hooks {

    @Before("@journal")
    public void loginBeforeJournalScenarios() {

        // 🔥 REQUIRED
        RestAssured.baseURI = "https://journal-app-km4z.onrender.com";

        System.out.println("🔥 @Before @journal hook executed");

        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                  "username": "testuser",
                                  "password": "pass1234"
                                }
                                """)
                        .post("/public/login");

        response.then().statusCode(200);

        String token = response.jsonPath().getString("token");
        Assert.assertNotNull("Token should not be null", token);

        context.put("token", token);

        System.out.println("🔥 Token stored: " + token);
    }
}
