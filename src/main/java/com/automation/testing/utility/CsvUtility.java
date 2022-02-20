package com.automation.testing.utility;

import com.automation.testing.core.TestArtifacts;
import com.automation.testing.model.KeyValuePair;
import com.automation.testing.model.TestSteps;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtility.class);

    /**
     * @param path path where csv is located
     * @param clazz the class where key-value property is located
     * @return a list of key-value pairs
     * */
    public static List<KeyValuePair> buildKeyValueMapping(String path, Class clazz) throws IOException {
        return buildKeyValueMapping(path, clazz, new ColumnPositionMappingStrategy());
    }

    /**
     * @param path path where csv is located
     * @param clazz the class where key-value property is located
     * @param mappingStrategy example ColumnPositionMappingStrategy
     * @return a list of key-value pairs
     * */
    public static List<KeyValuePair> buildKeyValueMapping(String strPath, Class clazz, MappingStrategy mappingStrategy) throws IOException {

        mappingStrategy.setType(clazz);

        var file = new File(strPath).getAbsolutePath();

        Path path = Paths.get(file);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz)
                .withMappingStrategy(mappingStrategy)
                .build();

        List<KeyValuePair> pairs = cb.parse();

        reader.close();

        return pairs;
    }

    /**
     * To get the list of test steps
     * @param path the path where test steps located
     * @return the list of TestSteps object
     * */

    public static List<TestSteps> buildTestSteps(Path path) throws IOException {
        return buildTestSteps(path, "default");
    }

    /**
     * To get the list of test steps
     * @param path the path where test steps located
     * @param environment specifies the environment to use
     * @return the list of TestSteps object
     * */
    public static List<TestSteps> buildTestSteps(Path path, String environment) throws IOException {
        List<TestSteps> stepsList = new ArrayList<>();

        if(StringUtils.isBlank(environment))
            environment = "default";

        Reader reader = Files.newBufferedReader(path);

        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        int valueIndex = -1;
        boolean bolIsHeader = true;
        while ((line = csvReader.readNext()) != null) {
            if(bolIsHeader){
                valueIndex = getValueIndex(line, environment);
                if(valueIndex == -1)
                    throw new IndexOutOfBoundsException("No environment value specified.");

                bolIsHeader = false;
                continue;
            }
            stepsList.add(getTestSteps(line, valueIndex));
        }
        reader.close();
        csvReader.close();

        return stepsList;
    }

    private static TestSteps getTestSteps(String[] line, int valueIndex){
        TestSteps testSteps = new TestSteps();
        testSteps.setStep(line[0]);
        testSteps.setScreenName(line[1]);
        testSteps.setElementName(line[2]);
        testSteps.setValue(line[valueIndex]);
        return testSteps;
    }

    private static int getValueIndex(String[] line, String environment){
        int index = -1;
        for (String s : line) {
            ++index;
            if(StringUtils.equalsIgnoreCase(s.trim(),environment+"_value"))
                return index;
        }
        return -1;
    }
}
