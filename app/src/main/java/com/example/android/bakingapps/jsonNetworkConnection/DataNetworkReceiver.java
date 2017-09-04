package com.example.android.bakingapps.jsonNetworkConnection;

import android.content.Context;



interface DataNetworkReceiver<DataType>
{
    DataType ReceiveData(String URL);
    boolean CheckConnection(Context context);
}
