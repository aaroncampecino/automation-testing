package com.automation.testing.model;

import com.opencsv.bean.CsvBindByPosition;

public class KeyValuePair{
    @CsvBindByPosition(position = 0)
    private String key;

    @CsvBindByPosition(position = 1)
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
