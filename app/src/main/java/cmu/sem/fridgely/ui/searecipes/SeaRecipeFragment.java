package cmu.sem.fridgely.ui.searecipes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.FilterObject;
import cmu.sem.fridgely.ui.recipes.RecipeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeaRecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeaRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeaRecipeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SeaRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeaRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeaRecipeFragment newInstance(String param1, String param2) {
        SeaRecipeFragment fragment = new SeaRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sea_recipe, container, false);

        prepareSearchView(root);
        prepareCuisineView(root);
        prepareFilterDrawer(root, ((MainActivity)getActivity()).getFilters());

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void doSearch(String query) {
        ArrayList<FilterObject> listFilter = ((MainActivity)getActivity()).getFilters();

        Context context = getContext();
        CharSequence text = "Trying to search for " + query;
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();

        // Connect to recipe fragment
        RecipeFragment recipeFragment = new RecipeFragment(query, listFilter);
        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction()
            .addToBackStack("recipefrag")
            .replace(R.id.nav_host_fragment, recipeFragment, "recipefrag")
            .commit();
    }

    private FloatingSearchView prepareSearchView(View root) {
        final FloatingSearchView floatingSearchView = root.findViewById(R.id.search_view);
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                doSearch(floatingSearchView.getQuery());
            }
        });

        return floatingSearchView;
//        final SearchView searchView = root.findViewById(R.id.search_view);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                doSearch(query);
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        return searchView;
    }

    private void prepareFilterDrawer(final View root, final ArrayList<FilterObject> filters){
        ImageButton filterButton = root.findViewById(R.id.filter_button);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FilterFragment(filters).show(getActivity().getSupportFragmentManager(), "filterfrag");
            }
        });
    }

    private void prepareCuisineView(View root) {
        final ImageView breakfast_btn = root.findViewById(R.id.cuisine_1);
        final TextView breakfast_title = root.findViewById(R.id.cuisine_1_title);
        breakfast_title.setText("Breakfast");
        breakfast_btn.setImageResource(R.drawable.breakfast_bg);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(breakfast_title.getText().toString());
            }
        });

        final ImageView indian_btn = root.findViewById(R.id.cuisine_2);
        final TextView indian_title = root.findViewById(R.id.cuisine_2_title);
        indian_title.setText("Indian");
        indian_btn.setImageResource(R.drawable.indian_bg);
        indian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(indian_title.getText().toString());
            }
        });

        final ImageView chinese_btn = root.findViewById(R.id.cuisine_3);
        final TextView chinese_title = root.findViewById(R.id.cuisine_3_title);
        chinese_title.setText("Chinese");
        chinese_btn.setImageResource(R.drawable.chinese_bg);
        chinese_btn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        chinese_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(chinese_title.getText().toString());
            }
        });

        final ImageView korean_btn = root.findViewById(R.id.cuisine_4);
        final TextView korean_title = root.findViewById(R.id.cuisine_4_title);
        korean_title.setText("Korean");
        korean_btn.setImageResource(R.drawable.korean_bg);
        korean_btn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        korean_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(korean_title.getText().toString());
            }
        });

        final ImageView italian_btn = root.findViewById(R.id.cuisine_5);
        final TextView italian_title = root.findViewById(R.id.cuisine_5_title);
        italian_title.setText("Italian");
        italian_btn.setImageResource(R.drawable.italian_bg);
        italian_btn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        italian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(italian_title.getText().toString());
            }
        });

        final ImageView more_btn = root.findViewById(R.id.cuisine_6);
        final TextView more_title = root.findViewById(R.id.cuisine_6_title);
        more_title.setText("More+");
        more_btn.setImageResource(R.drawable.more_bg);
        more_btn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(more_title.getText().toString());
            }
        });

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
