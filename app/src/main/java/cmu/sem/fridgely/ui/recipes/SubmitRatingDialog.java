package cmu.sem.fridgely.ui.recipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.Rating_Query;

public class SubmitRatingDialog extends DialogFragment {

    private String recipeId;

    public SubmitRatingDialog(String recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.dialog_submit_recipe_rate, null);
        final RatingBar ratingBar = viewInflated.findViewById(R.id.recipeRatingBarEdit);

        builder.setView(viewInflated);

        builder.setPositiveButton(R.string.item_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        submitRatings(ratingBar.getRating()+"");
                    }
                })
                .setNegativeButton(R.string.universal_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void submitRatings(String rating){
        // Create URL
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("fridgely", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        String url = getResources().getString(R.string.submit_ratings)+userId;

        // Create object for sending submit request
        Rating_Query rating_query = new Rating_Query();
        rating_query.setId(this.recipeId);
        rating_query.setRating(rating);
        String request = new Gson().toJson(rating_query);

        // Send request to server
        try{
            Log.d(getClass().getName(), "Inside submitRatings, request="+request);
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
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[submitRatings] Error: "+ex);
        }
    }
}
