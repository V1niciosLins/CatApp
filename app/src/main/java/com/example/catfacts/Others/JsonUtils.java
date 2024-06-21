package com.example.catfacts.Others;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

public class JsonUtils {
    public static List<String> readJson(Context context, int resourceId){
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        JsonObject jsonObject = JsonParser.parseReader(inputStreamReader).getAsJsonObject();

        Gson gson = new Gson();

        Type listType = new TypeToken<List<String>>(){}.getType();

        return gson.fromJson(jsonObject.get("facts_about_cats"),listType);
    }

    public static String getRandomFact(Context context, int resourceId){
        List<String> catFacts = readJson(context, resourceId);
        return catFacts.get(new Random().nextInt(catFacts.size()));
    }
}
