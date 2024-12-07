package com.example.androidproject.jsonHttps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsManager {
    private HttpsURLConnection connection;
    private BufferedReader reader;
    private String url;

    public HttpsManager(String URL) {
        this.url = URL;
    }

    public String procesare(){
        try {
            return httpsJsonToString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            inchidere();
        }
    }

    private void inchidere() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection.disconnect();
    }

    private String httpsJsonToString() throws IOException {
        connection = (HttpsURLConnection) new URL(url).openConnection();
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line=reader.readLine())!=null){
            builder.append(line);
        }

        return builder.toString();
    }
}
