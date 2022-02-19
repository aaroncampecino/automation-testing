package com.automation.testing.utility;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class Settings {

    private static ISettingsFile settingsFile;

    private Settings(){
        settingsFile = new JsonSettingsFile("settings.test.json");
    }

    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    /**
     * @return the path where the objects located
     * */
    public String getObjectsPath(){
        return settingsFile.getValue("/objectsPath").toString();
    }

    /**
     * starts with /<br />
     * example /homeAddress<br />
     * {
     *     "homeAddress" : "123 Test Address"
     * }
     * @return the setting defined in settings.test.json
     * */
    public String getSetting(String name){
        return settingsFile.getValue(name).toString();
    }
}
