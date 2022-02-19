package com.automation.testing.definitions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ITextBox;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class StepDefinition {

    @Given("I launch browser {string}")
    public void launchBrowser(String url) throws IOException {
        Browser browser = AqualityServices.getBrowser();

        browser.maximize();
        browser.goTo(url);
        browser.waitForPageToLoad();

        ISettingsFile settingsFile = getSettings();

        IElementFactory elementFactory = AqualityServices.getElementFactory();
        ITextBox txbSearch = elementFactory.getTextBox(By.xpath(settingsFile.getValue("/home/search_input_text/xpath").toString()), "Search");
        txbSearch.type("Avengers");
        //txbSearch.submit();

        By searchButton = By.xpath(settingsFile.getValue("/home/search_button/xpath").toString());
        IButton btnSearch = elementFactory.getButton(searchButton, "Search Button");
        btnSearch.click();

        browser.waitForPageToLoad();
    }

    public static ISettingsFile getSettings() throws IOException {
        //String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        return new JsonSettingsFile(getResourceFileByName("google/home.json"));
    }

    private static File getResourceFileByName(String fileName){
        return Paths.get(getProjectBaseDir(), "/src/main/resources/" + fileName).toFile();
    }

    private static String getProjectBaseDir(){
        return  System.getProperty("user.dir") != null ? System.getProperty("user.dir") : System.getProperty("project.basedir");
    }

    @Then("I execute test steps from {string}")
    public void executeSteps(String path){
        System.out.printf("Test "+path);
    }

}
