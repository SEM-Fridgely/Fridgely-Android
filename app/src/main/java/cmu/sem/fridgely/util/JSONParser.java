package cmu.sem.fridgely.util;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cmu.sem.fridgely.object.Data;
import cmu.sem.fridgely.object.QueryResults;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.object.RecipeData;
import cmu.sem.fridgely.object.User;
import cmu.sem.fridgely.object.hit;


public class JSONParser {

    public Data getUserFromUrl(String url){
        Gson gson = new Gson();
        User queryResults = gson.fromJson(url, User.class);
        if(queryResults.equals(null))
           return null;
        else
            return queryResults.getData();
    }

    public List<Recipe> getRecipiesFromUrl(String url){
        Gson gson = new Gson();
        RecipeData recipeData = gson.fromJson(url, RecipeData.class);
        System.out.println("[evanshwu] "+recipeData.data.get(0).getLabel());
        return recipeData.data;
    }

    public String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } catch(Exception ex){
            Log.d(this.getClass().getName(), "Error reading JSON from url: "+ex);
        } finally{
            if (reader != null)
                reader.close();
        }

        return "";
    }
}
