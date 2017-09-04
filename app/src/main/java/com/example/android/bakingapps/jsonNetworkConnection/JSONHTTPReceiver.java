package com.example.android.bakingapps.jsonNetworkConnection;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



public class JSONHTTPReceiver implements JSONReceiver
{
    @Override
    public String ReceiveData(String stringURL)
    {
        try
        {
            URL url = new URL(stringURL);
            HttpURLConnection urlConnection;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            urlConnection.disconnect();

            if (hasInput)
            {
                return scanner.next();
            }

            else
            {
                return null;
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean CheckConnection(Context context)
    {
        return NetworkConnectionChecker.CheckConnection(context);
    }


}
