package cmu.sem.fridgely.ui.recipes;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.IngrdientAdapter;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.ui.UIUtils;
import cmu.sem.fridgely.util.Formatter;

public class RecipeDetail extends Fragment {
    private Recipe r;

    public void setRecipe(Recipe recipe){
        r = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Recipe view does not require floating button
        ((MainActivity)getActivity()).hideFloatingActionButton();

        View root = inflater.inflate(R.layout.recipe_detail_layout, container, false);
        final ImageView recipePreviewImage = root.findViewById(R.id.recipe_preview_image);
        Glide.with(getActivity()).load(r.getImage()).into(recipePreviewImage);
        final TextView recipeTitle = root.findViewById(R.id.recipeTitle);
        String recipeUrl = "<a href=\""+r.getUrl()+"\">"+r.getLabel()+"</a>";
        recipeTitle.setText(Html.fromHtml(recipeUrl));
        recipeTitle.setMovementMethod(LinkMovementMethod.getInstance());
        final RatingBar ratings = root.findViewById(R.id.rating);
        ratings.setRating(r.getRating());
        ratings.setClickable(true);
        ratings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SubmitRatingDialog submitRatingDialog = new SubmitRatingDialog(r.getId());
                    submitRatingDialog.show(getFragmentManager(), "ratingDialog");
                }
                return true;
            }
        });
        final TextView ratingNum = root.findViewById(R.id.ratingNum);
        // TODO: handle while rating is null
        if(r.getRaterNum()==0){
            ratingNum.setText("-");
        }else{
            ratingNum.setText(r.getRaterNum()+"");
        }
        final TextView serveSize = root.findViewById(R.id.serving_number);
        serveSize.setText(r.getYield()+"");
        final TextView calories = root.findViewById(R.id.calorie_number);
        calories.setText(Formatter.castCaloriesToTwoDecimals(r.getCalories()));
        final ListView ingredientList = root.findViewById(R.id.ingredient_list);

        IngrdientAdapter ingrdientAdapter = new IngrdientAdapter(root.getContext(), 0, r.getIngredientLines());
        ingredientList.setAdapter(ingrdientAdapter);
        UIUtils.setListViewHeightBasedOnItems(ingredientList);

        return root;
    }


}
