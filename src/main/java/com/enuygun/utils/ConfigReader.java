package com.enuygun.utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream("src/main/resources/config.properties"),
                StandardCharsets.UTF_8)) {
            properties.load(reader);
            System.out.println("✅ Config loaded successfully from resources/config.properties");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Could not load config.properties: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
