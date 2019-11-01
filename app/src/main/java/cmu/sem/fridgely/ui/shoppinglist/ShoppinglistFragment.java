package cmu.sem.fridgely.ui.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.ShoppingListAdapter;
import cmu.sem.fridgely.object.ShoppingListItem;

public class ShoppinglistFragment extends Fragment {

    private ShoppinglistViewModel homeViewModel;
    public ArrayList<ShoppingListItem> items;
    private ShoppingListAdapter shoppingListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Show floating action button
        ((MainActivity) getActivity()).showFloatingActionButton();

        homeViewModel =
                ViewModelProviders.of(this).get(ShoppinglistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.shopping_list_view);

        items = new ArrayList<>();
        SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getPreferences(Context.MODE_PRIVATE);
        String shoppinglistJson = sharedPreferences.getString("shoppinglist", "");
        items = new Gson().fromJson(shoppinglistJson, new TypeToken<ArrayList<ShoppingListItem>>(){}.getType());

        // Set layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        shoppingListAdapter = new ShoppingListAdapter(items);
        recyclerView.setAdapter(shoppingListAdapter);

        return root;
    }

    public void insertNewItem(){
        shoppingListAdapter.notifyDataSetChanged();
    }

    public void updateItem(){

    }
}