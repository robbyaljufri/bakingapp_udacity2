package com.example.android.bakingapps.jsonParsing;

import org.json.JSONObject;



public interface JSONParser<DataType>
{
    DataType Parse(String jsonString);
}
