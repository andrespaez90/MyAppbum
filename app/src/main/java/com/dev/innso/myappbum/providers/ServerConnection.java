package com.dev.innso.myappbum.providers;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection {

    public static String requestPOST(String url, Pair<String,String>... data){
        StringBuilder stringBuilder;

        try {
            URL service = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) service.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();
            for( int i =0 ; i< data.length;i++){
                builder.appendQueryParameter(data[i].first, data[i].second);
            }
            String query = builder.build().getEncodedQuery();
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            InputStream in = connection.getInputStream();
            stringBuilder = readJSON(in);
            return (stringBuilder.toString());

        } catch (Exception e) {
            Log.e(ServerConnection.class.getName(),e.getMessage().toString());
            return e.getLocalizedMessage();
        }

    }


    private static StringBuilder readJSON(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        Log.e("ads", stringBuilder.toString());
        return stringBuilder;
    }

}
