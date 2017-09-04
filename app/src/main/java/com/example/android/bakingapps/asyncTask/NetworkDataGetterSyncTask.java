package com.example.android.bakingapps.asyncTask;

import android.os.AsyncTask;

import com.example.android.bakingapps.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.bakingapps.jsonNetworkConnection.JSONReceiver;
import com.example.android.bakingapps.jsonNetworkConnection.JSONokHttpReceiver;
import com.example.android.bakingapps.jsonParsing.JSONParser;

import org.json.JSONObject;

/**
 * Created by robby on 29/07/17.
 */

public class NetworkDataGetterSyncTask<Data> extends AsyncTask<String, Void, Data>
{
    private JSONReceiver jsonReceiver;
    private JSONParser<Data> jsonParser;
    private AsyncTaskCompleteListener<Data> dataAsyncTaskCompleteListener;

    public NetworkDataGetterSyncTask(JSONParser<Data> jsonParser, AsyncTaskCompleteListener<Data> dataAsyncTaskCompleteListener) {
        this.jsonReceiver = new JSONokHttpReceiver();
        this.jsonParser = jsonParser;
        this.dataAsyncTaskCompleteListener = dataAsyncTaskCompleteListener;
    }

    @Override
    protected Data doInBackground(String... params) {
        try
        {
            String url = params[0];

            String jsonString = jsonReceiver.ReceiveData(url);
            return jsonParser.Parse(jsonString);

        } catch (NullPointerException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Data data) {
        super.onPostExecute(data);

        dataAsyncTaskCompleteListener.onComplete(data);
    }
}
