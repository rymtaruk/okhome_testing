package com.okhome.connection.constant;

import android.annotation.SuppressLint;
import android.content.Context;

public class PropertiesData {
    @SuppressLint("StaticFieldLeak")
    private static PropertiesData instance;

    public final String apiKey = "563492ad6f917000010000016fdf1c26d37346f8a692efd7a7279141";

    public String baseUrl;
    public Context context;
    public boolean isDebug;

    public static PropertiesData getInstance() {
        if (instance == null) {
            instance = new PropertiesData();
        }
        return instance;
    }
}
