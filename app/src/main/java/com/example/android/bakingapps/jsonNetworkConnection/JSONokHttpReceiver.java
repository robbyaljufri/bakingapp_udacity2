package com.example.android.bakingapps.jsonNetworkConnection;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class JSONokHttpReceiver implements JSONReceiver
{
    @Override
    public String ReceiveData(String URL) {

        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();

            response.close();

            return json;

        } catch (IOException e)
        {
            return null;
        }
    }

    @Override
    public boolean CheckConnection(Context context) {
        return NetworkConnectionChecker.CheckConnection(context);
    }
}
