@DemoFeature
Feature: Demco - Search Avengers on google
  Scenario: Search Avengers on google
    Then I initialize fixture from "src/test/resources/teststeps/testdata.csv"
    Given I launch browser and navigate to "https://www.google.com/"
    Then I execute test steps from "src/test/resources/teststeps/SearchGoogle.csv"

    