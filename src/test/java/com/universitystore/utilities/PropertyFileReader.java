package com.universitystore.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
    private static Properties configFile;

    /**
     * Loads the properties from the specified file path.
     *
     * @param filePath The path to the properties file.
     * @throws IOException If there was an error reading the properties file.
     */
    public static void loadProperties(String filePath) throws IOException {
        String fullPath = System.getProperty("user.dir") + filePath;
        FileInputStream input = new FileInputStream(fullPath);
        configFile = new Properties();
        configFile.load(input);
        input.close();
    }

    /**
     * Gets a string value from the properties file.
     *
     * @param key The key of the property to get.
     * @return The string value of the property.
     */
    public static String getString(String key) {
        return configFile.getProperty(key);
    }

    /**
     * Gets an integer value from the properties file.
     *
     * @param key The key of the property to get.
     * @return The integer value of the property.
     */
    public static int getInt(String key) {
        return Integer.parseInt(configFile.getProperty(key));
    }

    /**
     * Gets a long value from the properties file.
     *
     * @param key The key of the property to get.
     * @return The long value of the property.
     */
    public static long getLong(String key) {
        return Long.parseLong(configFile.getProperty(key));
    }

    /**
     * Gets a double value from the properties file.
     *
     * @param key The key of the property to get.
     * @return The double value of the property.
     */
    public static double getDouble(String key) {
        return Double.parseDouble(configFile.getProperty(key));
    }

    /**
     * Gets a boolean value from the properties file.
     *
     * @param key The key of the property to get.
     * @return The boolean value of the property.
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(configFile.getProperty(key));
    }

    /**
     * Gets the value of a property with the specified key from the specified file path.
     *
     * @param filePath The path to the properties file.
     * @param key The key of the property to get.
     * @return The value of the property.
     * @throws IOException If there was an error reading the properties file.
     */
    public static String getPropertyValue(String filePath, String key) throws IOException {
        loadProperties(filePath);
        return getString(key);
    }
}
