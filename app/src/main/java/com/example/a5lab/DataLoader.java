package com.example.a5lab;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader {

    public List<Currency> loadCurrencies(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream stream = connection.getInputStream();
            Parser parser = new Parser();
            return parser.parseXML(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
