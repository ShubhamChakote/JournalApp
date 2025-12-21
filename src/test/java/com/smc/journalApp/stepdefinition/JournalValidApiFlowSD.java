package com.smc.journalApp.stepdefinition;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.smc.journalApp.context.TestContext.context;

public class JournalValidApiFlowSD {

    RequestSpecification request;
    Response response;

  //  static Map<String, Object> context;
    static String inputFilePath;

    public JournalValidApiFlowSD(){
       // context = new HashMap<>();
        inputFilePath = "src//test//resources//testdata//input//";
    }


    // ---------- GIVEN ----------

    @Given("the base API URL is {string}") //keep
    public void theBaseAPIURLIs(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    // ---------- WHEN ----------

    @When("I send a GET request to {string}") //keep
    public void iSendAGETRequestTo(String path) {
        response = given()
                .contentType("application/json")
                .get(path);
    }

    // ---------- THEN ----------

    @Then("the response status should be {int}") //keep
    public void theResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("the application should be fully loaded") //keep
    public void theApplicationShouldBeFullyLoaded() {
        // optional check – keeping empty as per your feature
    }


    @When("I send a POST request to {string} with body:")
    public void iSendAPOSTRequestToWithBody(String endpoint , String body) {

        try {
             request = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(body);

            response = request.post(endpoint);

            // Log response for debugging
            System.out.println("========================================");
            System.out.println("Endpoint: " + endpoint);
            System.out.println("Request Body: " + body);
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.asString());
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("❌ Error sending POST request: " + e.getMessage());
            throw new RuntimeException("Failed to send POST request to " + endpoint, e);
        }

    }

    @And("the response should match the login response schema")
    public void theResponseShouldMatchTheLoginResponseSchema() {

        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(
                new File("src/test/resources/schemas/login-response-schema.json")
        ));

    }

    @When("I send DELETE request to {string}")
    public void iSendDELETERequestTo(String endpoint) {

        Object tokenObj = context.get("token");
        if (tokenObj == null) {
            throw new IllegalStateException("Auth token is NULL. Login hook did not run.");
        }

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenObj)
                .delete(endpoint);

    }




    @When("I send a GET request to {string} with header:")
    public void iSendAGETRequestToWithHeader(String endpoint, DataTable headers) {

        Object tokenObj = context.get("token");

        if (tokenObj == null) {
            throw new IllegalStateException(
                    "Auth token is NULL. @Before @journal hook did not store token."
            );
        }

        String token = tokenObj.toString();

        Map<String, String> headerMap =
                new HashMap<>(headers.asMap(String.class, String.class));

        headerMap.replaceAll((k, v) ->
                v.contains("${authToken}")
                        ? v.replace("${authToken}", token)
                        : v
        );

        response = given()
                .contentType(ContentType.JSON)
                .headers(headerMap)
                .get(endpoint);
    }



    @And("the response should be a JSON array")
    public void theResponseShouldBeAJSONArray() {
        response.then()
                .assertThat()
                .body("", isA(java.util.List.class));
    }


    @And("the response should match the journal list schema")
    public void theResponseShouldMatchTheJournalListSchema() {
        response.then().
                assertThat().
                body(
                (JsonSchemaValidator.matchesJsonSchema
                        (new File("src/test/resources/schemas/journal-list-schema.json"))));
    }


    @When("I send a POST request to {string} with header:")
    public void iSendAPOSTRequestToWithHeader(String endpoint, DataTable headers){

        Map<String, String> headerMap =
                new HashMap<>(headers.asMap(String.class, String.class));

      headerMap.replaceAll((k,v) ->
              v.contains("${authToken}")
                      ? v.replace("${authToken}" , context.get("token").toString()) : v
      );

        request = given()
                .contentType(ContentType.JSON)
                .headers(headerMap);

    }


    @And("the request body is:")
    public void theRequestBodyIs(String body) {

        response = request
                .body(body)
                .post("/journal");



    }

    @And("the response should match the journal create response schema")
    public void theResponseShouldMatchTheJournalCreateResponseSchema() {

        response.then()
                .assertThat()
                .body(matchesJsonSchema(
                        new File("src/test/resources/schemas/journal-create-response-schema.json")
                ));
    }


    @And("the response field {string} should be {string}")
    public void theResponseFieldShouldBe(String field, String expectedValue) {

        response.then()
                .assertThat()
                .body(field, equalTo(expectedValue));

    }

    @And("store the authentication token")
    public void storeTheAuthenticationToken() {
        String token = response.jsonPath().getString("token");
        assertThat("Auth token should not be null", token, notNullValue());
        context.put("token", token);
    }

}
