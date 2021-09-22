package com.okhome.connection.retrofit;

import junit.framework.TestCase;

import org.junit.Test;

import okhttp3.OkHttpClient;

public class HttpConfigurationTest extends TestCase {

    @Test
    public void testGetClient() {
        HttpConfiguration.getClient();
    }
}