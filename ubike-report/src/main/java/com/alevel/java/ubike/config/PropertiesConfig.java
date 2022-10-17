package com.alevel.java.ubike.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

public class PropertiesConfig {


    public Properties jdbcProperties() {
        try (var in = getClass().getClassLoader().getResourceAsStream("jdbc.properties")) {
            var properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


}
