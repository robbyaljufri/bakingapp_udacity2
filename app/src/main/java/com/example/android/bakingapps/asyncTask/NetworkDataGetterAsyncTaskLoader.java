package com.example.android.bakingapps.asyncTask;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapps.jsonNetworkConnection.JSONHTTPReceiver;
import com.example.android.bakingapps.jsonNetworkConnection.JSONReceiver;
import com.example.android.bakingapps.jsonParsing.JSONParser;

import org.json.JSONObject;

/**
 * Created by robby on 29/07/17.
 */

public class NetworkDataGetterAsyncTaskLoader<Data> {

    private static final int DATA_GETTER_LOADER = 30;
    private static final String URL_KEY = "url";

    private AppCompatActivity activity;
    private JSONReceiver jsonReceiver;
    private JSONParser<Data> jsonParser;
    private AsyncTaskCompleteListener<Data> dataAsyncTaskCompleteListener;


    public NetworkDataGetterAsyncTaskLoader(AppCompatActivity activity, JSONParser<Data> jsonParser, AsyncTaskCompleteListener<Data> dataAsyncTaskCompleteListener) {
        this.activity = activity;
        this.jsonReceiver = new JSONHTTPReceiver();
        this.jsonParser = jsonParser;
        this.dataAsyncTaskCompleteListener = dataAsyncTaskCompleteListener;
    }

    public void Execute(String URL)
    {
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, URL);

        LoaderManager loaderManager = activity.getSupportLoaderManager();
        DataGetterAsync dataGetterAsync = new DataGetterAsync();

        Loader<Data> dataLoader = loaderManager.getLoader(DATA_GETTER_LOADER);

        if(dataLoader == null)
        {
            loaderManager.initLoader(DATA_GETTER_LOADER, bundle, dataGetterAsync);
        }

        else
        {
            loaderManager.restartLoader(DATA_GETTER_LOADER, bundle, dataGetterAsync);
        }
    }

    public class DataGetterAsync implements LoaderManager.LoaderCallbacks<Data> {

        @Override
        public Loader<Data> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<Data>(activity) {

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();

                    if(args == null)
                    {
                        return;
                    }

                    forceLoad();
                }

                @Override
                public Data loadInBackground() {

                    String url = args.getString(URL_KEY);

                    if(url == null || args.isEmpty())
                    {
                        return null;
                    }

                    try
                    {
                        String jsonString = jsonReceiver.ReceiveData(url);
                        return jsonParser.Parse(jsonString);
                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Data> loader, Data data) {
            dataAsyncTaskCompleteListener.onComplete(data);
        }

        @Override
        public void onLoaderReset(Loader<Data> loader) {

        }
    }

}
