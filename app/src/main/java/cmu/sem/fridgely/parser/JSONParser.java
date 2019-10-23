package cmu.sem.fridgely.parser;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import cmu.sem.fridgely.object.QueryResults;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.object.hit;


public class JSONParser {
    private final String APP_ID = "3ef87764";
    private final String APP_KEY = "f6329aeb0ce6a806b529977877a9b5a4";

    public ArrayList<Recipe> getRecipiesFromUrl(String url){
        Gson gson = new Gson();
        QueryResults queryResults = gson.fromJson(url, QueryResults.class);
        ArrayList<Recipe> recipes = new ArrayList<>();
        for(hit h : queryResults.hits){
            System.out.println("Found one record:"+h.recipe.getLabel());
            recipes.add(h.recipe);
        }
        return recipes;
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
