package com.automation.testing.model;

import com.automation.testing.utility.CsvBean;
import com.opencsv.bean.CsvBindByPosition;

public class Fixture{
    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
