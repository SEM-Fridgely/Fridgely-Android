package cmu.sem.fridgely.adapter;

import android.app.Activity;
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
import java.util.Random;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.Recipe;

public class RecipeAdapter extends ArrayAdapter<Recipe> implements View.OnClickListener {
    private Recipe currentRecipe;

    public RecipeAdapter(Activity context, ArrayList<Recipe> recipes){
        super(context, 0, recipes);
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create new View object
        View listedItemsView = convertView;
        if(listedItemsView==null){
            listedItemsView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);
        }

        currentRecipe = getItem(position);

        final TextView labelTextView = listedItemsView.findViewById(R.id.name);
        labelTextView.setText(currentRecipe.getLabel());

        final RatingBar ratings = listedItemsView.findViewById(R.id.rating);
        // Random ratings
        Random random = new Random();
        ratings.setRating(random.nextInt(5));

        final TextView calorie = listedItemsView.findViewById(R.id.calorie);
        calorie.setText(currentRecipe.getCalories());

        final TextView raterNum = listedItemsView.findViewById(R.id.raterNum);
        //raterNum.setText(currentRecipe.getRaterNum()+"");
        raterNum.setText(random.nextInt(500)+"");

        final ImageView preview = listedItemsView.findViewById(R.id.preview);
        Glide.with(listedItemsView).load(currentRecipe.getImage()).apply(RequestOptions.circleCropTransform()).into(preview);

        return listedItemsView;
    }
}
