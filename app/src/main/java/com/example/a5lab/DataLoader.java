package com.example.a5lab;

import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<String>> {

    private final OnDataLoadedCallback callback;
    private final OnErrorCallback errorCallback;

    public DataLoader(OnDataLoadedCallback callback, OnErrorCallback errorCallback) {
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    @Override
    protected List<String> doInBackground(String... urls) {
        try {
            Log.d("DataLoader", "Loading data from " + urls[0]);
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();

            Parser parser = new Parser();
            return parser.parseXML(stream);

        } catch (Exception e) {
            Log.e("DataLoader", "Error loading data", e);
            if (errorCallback != null) {
                errorCallback.onError(e);
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (result != null && callback != null) {
            callback.onDataLoaded(result);
        }
    }

    public interface OnDataLoadedCallback {
        void onDataLoaded(List<String> data);
    }

    public interface OnErrorCallback {
        void onError(Exception e);
    }
}
