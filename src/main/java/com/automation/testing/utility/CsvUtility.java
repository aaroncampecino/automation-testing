package com.automation.testing.utility;

import com.automation.testing.model.KeyValuePair;
import com.automation.testing.model.TestSteps;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvValidationException;
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
    private static boolean hasHeader = true;

    /**
     * if hasHeader is true, then remove the header
     * Default is true
     * */
    public static void setHasHeader(boolean hasHeader) {
        CsvUtility.hasHeader = hasHeader;
    }

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
    public static List<KeyValuePair> buildKeyValueMapping(String path, Class clazz, MappingStrategy mappingStrategy) throws IOException {

        mappingStrategy.setType(clazz);

        var file = new File(path).getAbsolutePath();

        Path paths = Paths.get(file);

        Reader reader = Files.newBufferedReader(paths);
        CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz)
                .withMappingStrategy(mappingStrategy)
                .build();

        List<KeyValuePair> pairs = cb.parse();

        reader.close();

        if(hasHeader)
            pairs.remove(0);
        return pairs;
    }

    /**
     * To get the list of test steps
     * @param path the path where test steps located
     * @return the list of TestSteps object
     * */

    public static List<TestSteps> buildTestSteps(String path) throws IOException, CsvValidationException {
        return buildTestSteps(path, "default");
    }

    /**
     * To get the list of test steps
     * @param path the path where test steps located
     * @param environment specifies the environment to use
     * @return the list of TestSteps object
     * */
    public static List<TestSteps> buildTestSteps(String path, String environment) throws IOException, CsvValidationException {
        List<TestSteps> stepsList = new ArrayList<>();

        if(StringUtils.isBlank(environment))
            environment = "default";

        var file = new File(path).getAbsolutePath();
        Path paths = Paths.get(file);

        Reader reader = Files.newBufferedReader(paths);

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
        testSteps.setStep(StringUtils.trimToEmpty(line[0]));
        testSteps.setScreenName(StringUtils.trimToEmpty(line[1]));
        testSteps.setElementName(StringUtils.trimToEmpty(line[2]));
        testSteps.setValue(StringUtils.trimToEmpty(line[valueIndex]));
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
