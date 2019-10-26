package cmu.sem.fridgely.ui.recipes;

import android.content.Context;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.IngrdientAdapter;
import cmu.sem.fridgely.object.Recipe;

public class RecipeDetail extends Fragment {
    private Recipe r;
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public RecipeDetail() {
//        // Required empty public constructor
//    }
//
//    public static RecipeDetail newInstance(String param1, String param2) {
//        RecipeDetail fragment = new RecipeDetail();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        final ImageView recipePreviewImage = root.findViewById(R.id.recipe_preview_image);
        Glide.with(this).load(r.getImage()).apply(RequestOptions.circleCropTransform()).into(recipePreviewImage);
        final TextView recipeTitle = root.findViewById(R.id.recipeTitle);
        recipeTitle.setText(r.getLabel());
        final RatingBar ratings = root.findViewById(R.id.rating);
        ratings.setRating(r.getRating());
        final TextView ratingNum = root.findViewById(R.id.raterNum);
        ratingNum.setText(r.getRaterNum());
        final TextView serveSize = root.findViewById(R.id.ratingNum);
        serveSize.setText(r.getYield());
        final TextView calories = root.findViewById(R.id.calorie_number);
        calories.setText(r.getCalories());
        final ListView ingredientList = root.findViewById(R.id.ingredient_list);
        IngrdientAdapter ingrdientAdapter = new IngrdientAdapter(root.getContext(), 0, r.getIngredientLines());
        ingredientList.setAdapter(ingrdientAdapter);

        return root;
    }
//
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
