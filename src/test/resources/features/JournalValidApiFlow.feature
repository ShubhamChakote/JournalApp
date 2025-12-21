Feature: Journal App - Valid API Flow

  This feature validates the happy-path flow of Journal App APIs
  including application availability, user lifecycle, authentication,
  and authorized journal operations using schema validation.

  Background:
    Given the base API URL is "https://journal-app-km4z.onrender.com"

  @smoke @health @basicflow
  Scenario: Verify application is up and health check API is reachable
    When I send a GET request to "/"
    Then the response status should be 200
    And the application should be fully loaded

    When I send a GET request to "/public/health-check"
    Then the response status should be 200

  @user @auth @basicflow
  Scenario: Create user and login successfully
    When I send a POST request to "/public/create-user" with body:
      """
      {
        "username": "automationtestuser",
        "password": "pass1234"
      }
      """
    Then the response status should be 200

    When I send a POST request to "/public/login" with body:
      """
      {
        "username": "automationtestuser",
        "password": "pass1234"
      }
      """
    Then the response status should be 200
    And store the authentication token
    And the response should match the login response schema

    When I send DELETE request to "/users"
    Then the response status should be 204

  @journal @get @basicflow
  Scenario: Fetch journal entries using valid authentication token
   # Given I have a valid authentication token
    When I send a GET request to "/journal" with header:
      | Authorization | Bearer ${authToken} |
    Then the response status should be 200
    And the response should be a JSON array
    And the response should match the journal list schema

  @journal @post @basicflow
  Scenario: Create a journal entry successfully
#    Given I have a valid authentication token
    When I send a POST request to "/journal" with header:
      | Authorization | Bearer ${authToken} |
    And the request body is:
      """
      {
        "title": "watched a movie",
        "content": "great movie by dc"
      }
      """
    Then the response status should be 201
    And the response should match the journal create response schema
    And the response field "title" should be "watched a movie"
    And the response field "content" should be "great movie by dc"
