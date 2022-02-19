package com.automation.testing.definitions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import com.automation.testing.utility.Settings;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserStepDefinition extends BaseStepDefinition{

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserStepDefinition.class);

    private static Browser browser;
    private static Scenario scenario;

    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
    }

    @After
    public void teardown(){
        if (autoCloseBrowser())
            browser.quit();
    }

    @Given("I launch browser and navigate to {string}")
    public void launchBrowser(String url) {
        browser = AqualityServices.getBrowser();
        LOGGER.info("Launching "+browser.getBrowserName()+" browser.");
        browser.maximize();
        browser.goTo(url);
        LOGGER.info("Navigating to url "+url);
        browser.waitForPageToLoad();
    }

    @Then("I execute test steps from {string}")
    public void executeSteps(String path) {
        LOGGER.info("Executing test steps from "+path);
    }

    @Then("I initialize fixture from {string}")
    public void initilizeFixture(String path){
        LOGGER.info("Initializing fixture "+path);
    }

    private boolean autoCloseBrowser() {
        Settings settings = Settings.getInstance();
        return (Boolean) settings.getSetting("/autoCloseBrowser");
    }
}
