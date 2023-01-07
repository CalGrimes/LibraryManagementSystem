package com.calgrimes.librarymanagementsystem.Helpers;

import com.google.gson.Gson;

public class JsonHelper
{
    /**
     * Deserialises a JSON file to an object.
     *
     * @param path the path to the JSON file to deserialise.
     * @param obj an object representation of the data being parsed from the JSON string.
     * @return an object pertaining to the parsed JSON string.
     */
    public static <T> T deserialise(String path, Class<T> obj)
    {
        return new Gson().fromJson(IOHelper.readString(path), obj);
    }
}