package com.automation.testing.definitions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import com.automation.testing.core.StepsService;
import com.automation.testing.core.TestArtifacts;
import com.automation.testing.core.exception.FrameworkException;
import com.automation.testing.model.KeyValuePair;
import com.automation.testing.model.TestSteps;
import com.automation.testing.utility.CsvUtility;
import com.automation.testing.utility.Properties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class BrowserStepDefinition extends BaseStepDefinition{

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserStepDefinition.class);

    private static Browser browser;
    private static Scenario scenario;

    Properties properties = Properties.getInstance();

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
    public void executeSteps(String path) throws IOException, FrameworkException {
        if(skipTest()){
            LOGGER.info("Skipping test");
            return;
        }
        LOGGER.info("Executing test steps from "+path);

        String environment = properties.getEnvironment();
        List<TestSteps> testSteps = CsvUtility.buildTestSteps(path, environment);

        StepsService stepsService = new StepsService()
                .setTestSteps(testSteps);
        stepsService.executeProcess();
    }

    @Then("I initialize fixture from {string}")
    public void initilizeFixture(String path) throws IOException {
        LOGGER.info("Initializing fixture from "+path);

        List<KeyValuePair> keyValuePairList = CsvUtility.buildKeyValueMapping(path, KeyValuePair.class);
        TestArtifacts testArtifacts = TestArtifacts.getInstance();
        testArtifacts.addKeyValuePair(keyValuePairList);
        testArtifacts.printTestArtifacts();
    }

    private boolean autoCloseBrowser() {
        return Boolean.valueOf(properties.getProperty("auto.close.browser"));
    }
}
