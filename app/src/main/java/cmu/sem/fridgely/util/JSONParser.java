package cmu.sem.fridgely.util;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cmu.sem.fridgely.object.Data;
import cmu.sem.fridgely.object.QueryResults;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.object.RecipeData;
import cmu.sem.fridgely.object.Recipe_mask;
import cmu.sem.fridgely.object.User;
import cmu.sem.fridgely.object.hit;


public class JSONParser {

    public List<Recipe> getRecipiesFromUrl(String url){
        Gson gson = new Gson();
        Log.d(getClass().getName(), "[getRecipiesFromUrl] Query url="+url);
        try {
            RecipeData recipeData = gson.fromJson(url, RecipeData.class);
            ArrayList<Recipe> recipes = new ArrayList<>();
            for(Recipe_mask mask : recipeData.data){
                recipes.add(mask.recipe);
            }
            return recipes;
        }catch (Exception ex){
            Log.e(getClass().getName(), "Error while converting json response to object! "+ex);
        }
        return null;
    }

    public String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[10240];
            while ((read = reader.read(chars)) != -1) {
                Log.d(getClass().getName(), "[readUrl]"+read);
                buffer.append(chars, 0, read);
            }
            Log.d(getClass().getName(), "[readUrl] Got string:"+buffer.toString());
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
