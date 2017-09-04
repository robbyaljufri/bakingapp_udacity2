package com.example.android.bakingapps.asyncTask;

import com.example.android.bakingapps.jsonParsing.JSONParser;

import java.util.ArrayList;

/**
 * Created by robby on 28/07/17.
 */

public class NetworkDataListGetterAsyncTask<Data>
{
    private AsyncTaskCompleteListener<ArrayList<Data>> asyncTaskCompleteListener;
    private ArrayList<String> URLArray;

    private ArrayList<Data> dataArrayList;
    private int dataGetterPosition = 0;
    private JSONParser<Data> jsonParser;

    public NetworkDataListGetterAsyncTask(AsyncTaskCompleteListener<ArrayList<Data>> asyncTaskCompleteListener, ArrayList<String> URLArray, JSONParser<Data> jsonParser) {
        this.asyncTaskCompleteListener = asyncTaskCompleteListener;
        this.URLArray = URLArray;
        this.dataArrayList = new ArrayList<>();
        this.jsonParser = jsonParser;
    }

    public void Execute()
    {
        GetDataAsync();
    }

    private void GetDataAsync()
    {
        NetworkDataGetterSyncTask<Data> networkDataGetterSyncTask = new NetworkDataGetterSyncTask<>(jsonParser, new DataListAsyncTaskCompleteListener());
        networkDataGetterSyncTask.execute(URLArray.get(dataGetterPosition));
    }

    private class DataListAsyncTaskCompleteListener implements AsyncTaskCompleteListener<Data>
    {
        @Override
        public void onComplete(Data data) {

            if(data == null)
            {
                asyncTaskCompleteListener.onComplete(null);
            }

            dataArrayList.add(data);
            dataGetterPosition++;

            if(dataGetterPosition < URLArray.size())
            {
                GetDataAsync();
            }

            else
            {
                asyncTaskCompleteListener.onComplete(dataArrayList);
            }
        }
    }
}
