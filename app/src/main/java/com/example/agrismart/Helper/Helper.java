package com.example.agrismart.Helper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Helper {
    static String stream = null;

    public Helper() {

    }

    public String getHTTPData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            if(httpURLConnection.getResponseCode() == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();
                httpURLConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Error",String.valueOf(e));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error",String.valueOf(e));
        }
        if(stream == null) {
            Log.e("Error","stream is null");
        }

        return stream;
    }
}

