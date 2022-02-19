package com.automation.testing.utility;

import com.automation.testing.model.Fixture;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvUtility {

    private static List<Fixture> csvLists;

    public static List<Fixture> beanBuilderExample(Path path, Class clazz) throws IOException {
        return beanBuilderExample(path, clazz, new ColumnPositionMappingStrategy());
    }

    public static List<Fixture> beanBuilderExample(Path path, Class clazz, MappingStrategy ms) throws IOException {

        ms.setType(clazz);

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz)
                .withMappingStrategy(ms)
                .build();

        setCsvList(cb.parse());
        reader.close();

        return getCsvList();
    }

    private static void setCsvList(List<Fixture> csvList) {
        csvLists = csvList;
    }

    public static List<Fixture> getCsvList() {
        if (csvLists != null) return csvLists;
        return new ArrayList<>();
    }

}
