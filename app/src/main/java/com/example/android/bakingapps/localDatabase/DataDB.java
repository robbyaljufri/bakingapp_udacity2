package com.example.android.bakingapps.localDatabase;

import android.content.Context;

import java.util.ArrayList;


public abstract class DataDB<Data>
{
    Context context;

    public DataDB(Context context)
    {
        this.context = context;
    }

    public abstract ArrayList<Data> getAllData();
    public abstract void addData(Data data);
    public abstract void removeData(String idData);
    public abstract void removeAllData();
}
