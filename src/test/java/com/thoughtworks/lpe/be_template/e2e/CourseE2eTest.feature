Feature: Get courses

  Background:
    * configure ssl = true
    * header Authorization = 'Bearer ' + token
    * url baseUrl

  Scenario: Get course details
    Given path '/api/v1/course/1'
    And request {}
    When method GET
    Then status 200
    And match response.name == 'Course test first'
    And match response.description == 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium'
    And match response.price == 150