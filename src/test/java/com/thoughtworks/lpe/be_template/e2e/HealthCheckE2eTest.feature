Feature: Get health check

  Background:
    * configure ssl = true
    * url baseUrl

  Scenario: Get health check
    Given path '/actuator/health'
    And request {}
    When method GET
    Then status 200
    And match response.status == 'UP'