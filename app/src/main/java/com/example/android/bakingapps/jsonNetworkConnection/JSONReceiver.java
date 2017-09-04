package com.example.android.bakingapps.jsonNetworkConnection;

import android.content.Context;

import org.json.JSONObject;



public interface JSONReceiver extends DataNetworkReceiver<String>
{
    @Override
    String ReceiveData(String URL);

    @Override
    boolean CheckConnection(Context context);
}
