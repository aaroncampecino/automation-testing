package com.automation.testing.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Properties {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtility.class);
    private static java.util.Properties properties;
    private static Properties instance;

    private Properties() {
        properties = new java.util.Properties();

        try (InputStream input = new FileInputStream("src/main/resources/environment.properties")) {

            properties.load(input);

            loadSystemProperties();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadSystemProperties(){
        java.util.Properties props = System.getProperties();
        props.forEach((k,v)->{
            if(properties.getProperty(k.toString()) != null){
                properties.setProperty(k.toString(),v.toString());
            }
        });
    }

    public static Properties getInstance() {
        if (instance == null) {
            instance = new Properties();
        }
        return instance;
    }

    /**
     * @return the path where the objects located
     */
    public String getObjectsPath() {
        return this.getProperty("objects.path");
    }

    /**
     * @return the environment to use
     */
    public String getEnvironment() {
        return this.getProperty("environment");
    }

    /**
     * @param name property name
     * @return the value of property name
     */
    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
