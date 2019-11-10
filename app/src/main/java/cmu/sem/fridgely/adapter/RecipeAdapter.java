package cmu.sem.fridgely.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.util.Formatter;

public class RecipeAdapter extends ArrayAdapter<Recipe>  {
    private Recipe currentRecipe;

    public RecipeAdapter(Activity context, ArrayList<Recipe> recipes){
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create new View object
        View listedItemsView = convertView;
        if(listedItemsView==null){
            listedItemsView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);
        }

        currentRecipe = getItem(position);

        final TextView labelTextView = listedItemsView.findViewById(R.id.shop_item_name);
        labelTextView.setText(currentRecipe.getLabel());

        final RatingBar ratings = listedItemsView.findViewById(R.id.rating);
        if(currentRecipe.getRating()==0) {
            ratings.setVisibility(View.INVISIBLE);
        }else {
            ratings.setRating(currentRecipe.getRating());
        }

        final TextView calorie = listedItemsView.findViewById(R.id.calorie);
        calorie.setText(Formatter.castCaloriesToTwoDecimals(currentRecipe.getCalories()));

        final TextView raterNum = listedItemsView.findViewById(R.id.raterNum);
        if(currentRecipe.getRaterNum()==0){
            raterNum.setVisibility(View.INVISIBLE);
            ratings.setVisibility(View.INVISIBLE);
        }else {
            raterNum.setText(currentRecipe.getRaterNum() + "");
        }

        final ImageView preview = listedItemsView.findViewById(R.id.preview);
        Log.e(getClass().getName(), "Load image url="+currentRecipe.getImage());
        Glide.with(listedItemsView).load(currentRecipe.getImage()).apply(RequestOptions.circleCropTransform()).into(preview);

        return listedItemsView;
    }
}
