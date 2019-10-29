package cmu.sem.fridgely.ui.recipes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.RecipeAdapter;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.util.JSONParser;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;
    private ListView listView;
    private ArrayList<Recipe> recipeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        ((MainActivity)getActivity()).hideFloatingActionButton();
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        listView = root.findViewById(R.id.listView);
        // TODO: Change to adapter and hide api info
        String testlink = "https://api.edamam.com/search?q=steak&app_id=3ef87764&app_key=f6329aeb0ce6a806b529977877a9b5a4%20&from=0&to=10&calories=700-800&diet=low-fat";
        new StartAsyncTask().execute(testlink);

        return root;
    }

    // Params: String(url), Void, ArrayList<Recipe>
    public class StartAsyncTask extends AsyncTask<String, Void, ArrayList<Recipe>>{
        @Override
        protected ArrayList<Recipe> doInBackground(String... strings) {
            ArrayList<Recipe> recipes = new ArrayList<>();
            JSONParser jsonParser = new JSONParser();
            try {
                recipes.addAll(jsonParser.getRecipiesFromUrl(jsonParser.readUrl(strings[0])));
            }catch(Exception ex){
                System.out.println("Error merging recipes into list: "+ex);
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes){
            super.onPostExecute(recipes);
            RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(), recipes);
            listView.setAdapter(recipeAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Recipe recipe = (Recipe) listView.getAdapter().getItem(position);
                    System.out.println("Selected "+position+"th item, named "+recipe.getLabel()+
                                        ", size "+recipe.getYield()+", calorie "+recipe.getCalories());
                    RecipeDetail recipeDetail = new RecipeDetail();
                    recipeDetail.setRecipe(recipe);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, recipeDetail);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}