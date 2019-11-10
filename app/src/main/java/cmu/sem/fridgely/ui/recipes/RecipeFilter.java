package cmu.sem.fridgely.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;

public class RecipeFilter extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Recipe view does not require floating button
        ((MainActivity)getActivity()).hideFloatingActionButton();

        View root = inflater.inflate(R.layout.fragment_recipe_filter, container, false);


        return root;
    }
}
