package com.langchainbeam.utils;

import java.util.Map;

import com.google.gson.Gson;

public class JsonUtils {

    /**
     * Converts a Map to a JSON string.
     * 
     * @param map the Map to convert to JSON.
     * @return the JSON string representation of the Map.
     */
    public static String mapToJson(Map<String, String> outputFormat) {

        Gson gson = new Gson();
        String formatString = gson.toJson(outputFormat);
        return formatString;
    }
}
