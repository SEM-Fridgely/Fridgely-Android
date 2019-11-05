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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.ShoppingListAdapter;
import cmu.sem.fridgely.object.ShoppingListItem;

public class ShoppinglistFragment extends Fragment {
    //TODO: Implement Enhanced RecyclerView https://github.com/nikhilpanju/RecyclerViewEnhanced
    private ShoppinglistViewModel shoppingListViewModel;
    private ShoppingListAdapter shoppingListAdapter;
    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerTouchListener recyclerTouchListener;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Show floating action button
        ((MainActivity) getActivity()).showFloatingActionButton();

        shoppingListViewModel =
                ViewModelProviders.of(this).get(ShoppinglistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        recyclerView = root.findViewById(R.id.shopping_list_view);

        // Set layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getPreferences(Context.MODE_PRIVATE);
        String shoppinglistJson = sharedPreferences.getString("shoppinglist", "");
        ArrayList<ShoppingListItem> items = new Gson().fromJson(shoppinglistJson, new TypeToken<ArrayList<ShoppingListItem>>(){}.getType());

        // Set adapter
        shoppingListAdapter = new ShoppingListAdapter(items);
        recyclerView.setAdapter(shoppingListAdapter);

        // Set up enhanced feature
        SwipeController swipeController = new SwipeController();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
//        recyclerTouchListener = new RecyclerTouchListener(((MainActivity) getActivity()), recyclerView);
//        recyclerTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
//            @Override
//            public void onRowClicked(int position) {
//                System.out.println("At onRowClicked Row "+(position+1));
//            }
//
//            @Override
//            public void onIndependentViewClicked(int independentViewID, int position) {
//                System.out.println("At onIndependentViewClicked Row "+(position+1));
//            }
//        }).setSwipeOptionViews(R.id.edit, R.id.change)
//                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
//                    @Override
//                    public void onSwipeOptionClicked(int viewID, int position) {
//                        String message = "";
//                        if (viewID == R.id.edit) {
//                            message += "Edit";
//                        } else if (viewID == R.id.change) {
//                            message += "Change";
//                        }
//                        message += " clicked for row " + (position + 1);
//                        System.out.println("[ShoppinglistFragment]"+message);
//                    }
//                });
        //recyclerView.addOnItemTouchListener(recyclerTouchListener);

        // Setup viewmodel data
        shoppingListViewModel.postAllItems(items);

        shoppingListViewModel.getItems().observe(this, new Observer<ArrayList<ShoppingListItem>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ShoppingListItem> s) {
                System.out.println("Inside observer ");
//                SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getPreferences(Context.MODE_PRIVATE);
//                String shoppinglistJson = sharedPreferences.getString("shoppinglist", "");
//                ArrayList<ShoppingListItem> items = new Gson().fromJson(shoppinglistJson, new TypeToken<ArrayList<ShoppingListItem>>(){}.getType());
                ArrayList<ShoppingListItem> items = shoppingListViewModel.getItems().getValue();
                // Set adapter
                shoppingListAdapter = new ShoppingListAdapter(items);
                recyclerView.setAdapter(shoppingListAdapter);
            }
        });

        // Test area
        System.out.println("fragment id="+this.getId()+", tag="+this.getTag());

        return root;
    }

    public void insertNewItem(ShoppingListItem newItem){
        shoppingListViewModel.postItems(newItem);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        recyclerView.addOnItemTouchListener(recyclerTouchListener);
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        recyclerView.removeOnItemTouchListener(recyclerTouchListener);
//    }
}