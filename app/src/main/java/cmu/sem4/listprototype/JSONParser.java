package cmu.sem4.listprototype;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class JSONParser {
    private final String APP_ID = "3ef87764";
    private final String APP_KEY = "f6329aeb0ce6a806b529977877a9b5a4";

    public ArrayList<Recipe> getRecipiesFromUrl(String url){
        Gson gson = new Gson();
        QueryResults queryResults = gson.fromJson(url, QueryResults.class);
        ArrayList<Recipe> recipes = new ArrayList<>();
        for(hit h : queryResults.hits){
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
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
