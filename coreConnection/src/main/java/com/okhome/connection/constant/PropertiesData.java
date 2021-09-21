package com.okhome.connection.constant;

public class PropertiesData {
    private static PropertiesData instance;

    public final String apiKey = "563492ad6f917000010000016fdf1c26d37346f8a692efd7a7279141";

    public String baseUrl;

    public static PropertiesData getInstance() {
        if (instance == null) {
            instance = new PropertiesData();
        }
        return instance;
    }
}
