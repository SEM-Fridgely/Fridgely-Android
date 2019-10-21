package cmu.sem4.listprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<Recipe> recipes = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            recipes.addAll(jsonParser.getRecipiesFromUrl(jsonParser.readUrl("https://api.edamam.com/search?q=egg&app_id=3ef87764&app_key=f6329aeb0ce6a806b529977877a9b5a4%20&from=0&to=10&calories=700-800&diet=low-fat")));

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }


        RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipes);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(recipeAdapter);
    }


}
