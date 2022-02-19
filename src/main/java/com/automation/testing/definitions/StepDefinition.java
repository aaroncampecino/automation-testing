package com.automation.testing.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinition {

    @Given("I launch browser")
    public void launchBrowser(){
        System.out.printf("Launching browser");
    }

    @Then("I execute test steps from {string}")
    public void executeSteps(String path){
        System.out.printf("Test "+path);
    }

}
