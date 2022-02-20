package com.automation.testing.core;

import com.automation.testing.model.KeyValuePair;
import com.automation.testing.utility.CsvUtility;
import com.automation.testing.utility.TableGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TestArtifacts {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestArtifacts.class);

    private static TestArtifacts instance;
    private Map<String, String> artifacts = new TreeMap<>();

    private TestArtifacts(){}

    public static TestArtifacts getInstance(){
        if(instance == null)
            instance = new TestArtifacts();

        return instance;
    }

    public Map<String, String> getArtifacts(){
        return artifacts;
    }

    public void putArtifacts(String key, String value){
        if(StringUtils.isBlank(key))
            return;
        artifacts.put(key, value);
    }

    public void addKeyValuePair(List<KeyValuePair> keyValuePairList){
        keyValuePairList.stream().filter(e->e.getKey()!=null && e.getValue()!= null).forEach(e->{
            putArtifacts(e.getKey().trim(),e.getValue().trim());
        });
    }

    public void printTestArtifacts(){
        if(artifacts.isEmpty()) {
            LOGGER.info("No test artifacts recovered.");
            return;
        }

        List<String> headers = Arrays.asList("Key","Value");

        List<List<String>> data = new ArrayList<>();
        List<String> row;

        for (var entry : artifacts.entrySet()) {

            row = new ArrayList<>();
            row.add(entry.getKey());
            row.add(entry.getValue());
            data.add(row);
        }

        TableGenerator.generateTable(headers, data);

    }

}
