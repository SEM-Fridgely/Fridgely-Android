package cmu.sem.fridgely.ui.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import org.json.JSONObject;

import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.adapter.ShoppingListAdapter;
import cmu.sem.fridgely.object.ShoppingListItem_Query;
import cmu.sem.fridgely.object.ShoppingListItem_QueryHead;
import cmu.sem.fridgely.object.ShoppingListItem_ReturnHead;

public class ShoppinglistFragment extends Fragment {
    //TODO: Implement Enhanced RecyclerView https://github.com/nikhilpanju/RecyclerViewEnhanced
    private ShoppinglistViewModel shoppingListViewModel;
    private ShoppingListAdapter shoppingListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerTouchListener recyclerTouchListener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        // Fetch data from server
        SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getSharedPreferences("fridgely", Context.MODE_PRIVATE);
        String shoppinglistId = sharedPreferences.getString("shoppinglistid", "");
        fetchShoppingList(getResources().getString(R.string.fetch_shoplist)+"/"+shoppinglistId, null);

        // Set up pull refresh layout
        swipeRefreshLayout = root.findViewById(R.id.shoppinglistswiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Update shopping list detected!!");
                refreshShopListView();
            }
        });

        // Setup viewmodel data
        shoppingListViewModel.getItems().observe(this, new Observer<ArrayList<ShoppingListItem_Query>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ShoppingListItem_Query> s) {
                System.out.println("Inside observer");
                ArrayList<ShoppingListItem_Query> items = shoppingListViewModel.getItems().getValue();
                shoppingListAdapter = new ShoppingListAdapter(items);
                recyclerView.setAdapter(shoppingListAdapter);
            }
        });


        // Set up enhanced feature - fanfatal
        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);
                System.out.println("left clicked at "+position);
            }

            @Override
            public void onRightClicked(int position) {
                super.onRightClicked(position);
                System.out.println("right clicked at "+position);
                // Delete
                shoppingListViewModel.deleteItem(position);
                // Send update request
                sendUpdateForShoppingList();
                refreshShopListView();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                swipeController.onDraw(c);
            }
        });

        return root;
    }

    public void insertNewItem(ShoppingListItem_Query newItem){
        shoppingListViewModel.postItems(newItem);
        sendUpdateForShoppingList();
    }

    public boolean fetchShoppingList(String url, ShoppingListItem_QueryHead request){
        // Build request json
        String jsonRequest = new Gson().toJson(request);

        try{
            Log.d(getClass().getName(), "Inside fetchShoppingList, request="+jsonRequest);
            // Build request object
            JSONObject object;
            if(!jsonRequest.equals("") && !jsonRequest.equals("null"))
                object = new JSONObject(jsonRequest);
            else
                object = null;
            // Listener
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(this.getClass().getName(), "Got response "+response.toString());
                    try {
//                        ShoppingListItem_ReturnHeadCollection returnHeadCollection =
                        ShoppingListItem_ReturnHead returnHead = new Gson().fromJson(response.toString(), ShoppingListItem_ReturnHead.class);
                        if(returnHead.getData()==null){
                            Log.d(getClass().getName(), "Got null response fetching shopping list. Ignore this if its an update or get action.");
                            return;
                        }
                        shoppingListViewModel.postAllItems(returnHead.getData().getItems());
                        Log.d(getTag(), "Convert to list object size="+returnHead.getData().getItems().size());
                        // Save to sharedpreference
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("fridgely", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("shoppinglist", new Gson().toJson(returnHead));
                        editor.commit();
                        // Reload view
                        refreshShopListView();
                    } catch (Exception e) {
                        Log.e(this.getClass().getName(), e.getMessage(), e);
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(this.getClass().getName(), "Error: " + error.getMessage());
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, object, listener, errorListener);

            //LoginActivity.getInstance().addToRequestQueue(jsonObjectRequest);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[fetchShopList] Error: "+ex);
        }
        return true;
    }

    public void sendUpdateForShoppingList(){
        SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getSharedPreferences("fridgely", Context.MODE_PRIVATE);
        String shoppinglistId = sharedPreferences.getString("shoppinglistid", "");
        String url = getResources().getString(R.string.fetch_shoplist)+"/"+shoppinglistId;
        ShoppingListItem_QueryHead queryHead = new ShoppingListItem_QueryHead();
        queryHead.setName("");
        queryHead.setItems(shoppingListViewModel.getItems().getValue());
        String request = new Gson().toJson(queryHead);

        try{
            Log.d(getClass().getName(), "Inside sendUpdateForShoppingList, request="+request);
            // Build request object
            JSONObject object;
            if(!request.equals("") && !request.equals("null"))
                object = new JSONObject(request);
            else
                object = null;
            // Listener
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(this.getClass().getName(), "Got response "+response.toString());
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(this.getClass().getName(), "Error: " + error.getMessage());
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[sendUpdateForShoppingList] Error: "+ex);
        }
    }

    public void refreshShopListView(){
        shoppingListAdapter = new ShoppingListAdapter(shoppingListViewModel.getItems().getValue());
        Log.d(getTag(), "Counts = "+shoppingListAdapter.getItemCount());
        recyclerView.setAdapter(shoppingListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }
}