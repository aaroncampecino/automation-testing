package com.automation.testing.core;

import aquality.selenium.core.utilities.JsonSettingsFile;
import com.automation.testing.constant.ElementType;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;

public class JsonSettings extends JsonSettingsFile {

    private String screenName;

    public JsonSettings(File file) throws IOException {
        super(file);
    }

    public JsonSettings(String resourceName) {
        super(resourceName);
    }

    public JsonSettings setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    /**
     * @param elementName Name of the element
     * @return XPath of the element specified
     * */
    public By getXPath(String elementName){
        String xpath = this.getValue("/"+screenName+"/"+elementName+"/xpath").toString();
        return By.xpath(xpath);
    }

    public ElementType getElementType(String elementName){
        String elementType = this.getValue("/"+screenName+"/"+elementName+"/type").toString();
        return ElementType.valueOf(elementType);
    }

}
