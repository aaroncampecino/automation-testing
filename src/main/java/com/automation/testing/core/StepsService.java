package com.automation.testing.core;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.*;
import com.automation.testing.constant.ElementType;
import com.automation.testing.core.exception.FrameworkException;
import com.automation.testing.definitions.BrowserStepDefinition;
import com.automation.testing.model.TestSteps;
import com.automation.testing.utility.Properties;
import com.automation.testing.utility.TableGenerator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class StepsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepsService.class);

    private List<TestSteps> testSteps;
    private WebDriver webDriver;
    private Map<String, JsonSettings> settingsFileMap = new HashMap<>();

    private Properties properties = Properties.getInstance();

    public StepsService(){
        webDriver = AqualityServices.getBrowser().getDriver();
    }

    public StepsService setTestSteps(List<TestSteps> testSteps){
        this.testSteps = testSteps;
        return this;
    }

    public boolean executeProcess() throws FrameworkException {
        if(this.testSteps == null || this.testSteps.size() == 0)
            throw new FrameworkException("Test steps is missing.");

        printTestSteps();
        generateObjectsSettings();

        String steps;
        String screenName;
        String elementName;
        String value;

        JsonSettings settingsFile;
        By xpath;
        ElementType elementType;
        IElementFactory elementFactory = AqualityServices.getElementFactory();
        for (TestSteps testStep : testSteps) {

            steps = testStep.getStep();
            LOGGER.info("Executing steps : "+steps);
            screenName = testStep.getScreenName();
            elementName = testStep.getElementName();
            value = testStep.getValue();

            settingsFile = settingsFileMap.get(screenName);

            xpath = settingsFile.getXPath(elementName);
            elementType = settingsFile.getElementType(elementName);

            switch (elementType){
                case TEXTBOX :
                    if(StringUtils.isBlank(value))
                        throw new FrameworkException("Empty value.");
                    ITextBox textBox = elementFactory.getTextBox(xpath, elementName);
                    textBox.clearAndType(value);
                    break;
                case BUTTON:
                    IButton button = elementFactory.getButton(xpath, elementName);
                    button.clickAndWait();
                    break;
                case CHECKBOX:
                    ICheckBox checkbox = elementFactory.getCheckBox(xpath, elementName);
                    checkbox.check();
                    break;
                case LINK:
                    ILink link = elementFactory.getLink(xpath, elementName);
                    link.clickAndWait();
                    break;
                case IMAGE:
                    break;
                case LABEL:
                    break;
                case COMBOBOX:
                    break;
                case RADIOBUTTON:
                    break;
                default:
                    throw new FrameworkException("Invalid argument.");
            }
        }

        return true;
    }

    private void generateObjectsSettings(){
        Set<String> screenNames = testSteps.stream().map(TestSteps::getScreenName).collect(Collectors.toSet());

        String objectsPath = properties.getObjectsPath();
        for (String screenName : screenNames) {
            JsonSettings settingsFile = new JsonSettings(objectsPath+"/"+screenName+".json")
                    .setScreenName(screenName);

            settingsFileMap.put(screenName, settingsFile);
        }
    }

    private void printTestSteps() throws FrameworkException {
        if(this.testSteps == null || this.testSteps.size() == 0)
            throw new FrameworkException("Test steps is missing.");

        List<String> headers = Arrays.asList("Steps","ScreenName","ElementName","Value");

        List<List<String>> data = new ArrayList<>();
        List<String> row;

        for (var step : testSteps) {

            row = new ArrayList<>();
            row.add(step.getStep());
            row.add(step.getScreenName());
            row.add(step.getElementName());
            row.add(step.getValue());
            data.add(row);
        }

        TableGenerator.generateTable(headers, data);
    }

}
