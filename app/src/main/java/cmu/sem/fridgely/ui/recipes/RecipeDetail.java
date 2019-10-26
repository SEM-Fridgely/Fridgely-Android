package cmu.sem.fridgely.ui.recipes;

import android.content.Context;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.IngrdientAdapter;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.ui.UIUtils;

public class RecipeDetail extends Fragment {
    private Recipe r;

    public void setRecipe(Recipe recipe){
        r = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.recipe_detail_layout, container, false);
        final ImageView recipePreviewImage = root.findViewById(R.id.recipe_preview_image);
        Glide.with(getActivity()).load(r.getImage()).into(recipePreviewImage);
        final TextView recipeTitle = root.findViewById(R.id.recipeTitle);
        recipeTitle.setText(r.getLabel());
        final RatingBar ratings = root.findViewById(R.id.rating);
        ratings.setRating(r.getRating());
        final TextView ratingNum = root.findViewById(R.id.ratingNum);
        // TODO: handle while rating is null
//        ratingNum.setText(r.getRaterNum());
        ratingNum.setText("500");
        final TextView serveSize = root.findViewById(R.id.serving_number);
        serveSize.setText(r.getYield()+"");
        final TextView calories = root.findViewById(R.id.calorie_number);
        calories.setText(r.getCalories());
        final ListView ingredientList = root.findViewById(R.id.ingredient_list);

        IngrdientAdapter ingrdientAdapter = new IngrdientAdapter(root.getContext(), 0, r.getIngredientLines());
        ingredientList.setAdapter(ingrdientAdapter);
        UIUtils.setListViewHeightBasedOnItems(ingredientList);

        return root;
    }
}
