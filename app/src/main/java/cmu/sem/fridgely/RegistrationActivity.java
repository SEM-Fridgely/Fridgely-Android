package cmu.sem.fridgely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import cmu.sem.fridgely.object.ShoppingListItem_Query;
import cmu.sem.fridgely.object.ShoppingListItem_QueryHead;
import cmu.sem.fridgely.object.ShoppingListItem_ReturnHead;
import cmu.sem.fridgely.object.User;
import cmu.sem.fridgely.object.UserRegister;

public class RegistrationActivity extends AppCompatActivity {
    private EditText username;
    private EditText useremail;
    private EditText userpwd;
    private EditText userpwd_c;
    private TextView indicator;
    private Button regSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        indicator = findViewById(R.id.pwdmatchindicator);
        indicator.setVisibility(View.INVISIBLE);

        regSubmit = findViewById(R.id.reg_submit_btn);
        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get items
                username = findViewById(R.id.reg_name_input);
                useremail = findViewById(R.id.reg_email_input);
                userpwd = findViewById(R.id.reg_pwd_input);
                userpwd_c = findViewById(R.id.reg_pwd_input_confirm);

                // Check for nulls
                if(username.getText().toString().matches("")){
                    username.setError("Your name should not be empty");
                    return;
                }

                if(useremail.getText().toString().matches("")){
                    useremail.setError("Your email should not be empty");
                    return;
                }

                //TODO: check for email duplication

                // Confirm that pwd is the same
                if(!userpwd.getText().toString().matches(userpwd_c.getText().toString())){
                    userpwd_c.setError("The passwords do not match");
                    return;
                }

                // Send register!
                //TODO: Send some data back to server!!!
                UserRegister userRegister = new UserRegister();
                userRegister.setEmail(useremail.getText().toString());
                userRegister.setPassword(userpwd.getText().toString());
                userRegister.setUsername(username.getText().toString());
                if(!registerUser(userRegister)){
                    System.out.println("Error while registering new user!");
                    return;
                }
            }
        });

    }

    public boolean registerUser(UserRegister userRegister){

        String jsonRequest = new Gson().toJson(userRegister);
        try{
            Log.d(getClass().getName(), "Inside fetchData, request="+jsonRequest);
            // Build request object
            JSONObject object;
            if(jsonRequest!=null)
                object = new JSONObject(jsonRequest);
            else
                object = null;
            // Listener
            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(this.getClass().getName(), "Got response "+response.toString());
                    try {
                        User user = new Gson().fromJson(response.toString(), User.class);
                        if(user.getData()==null){
                            indicator.setVisibility(View.VISIBLE);
                            return;
                        }
                        // Save to sharedpreference
                        SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", user.getData().getId());
                        editor.putString("fridgelier", user.getData().getUsername());
                        editor.commit();
                        createNewShoppingList(user.getData().getId(), user.getData().getUsername());
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
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getResources().getString(R.string.fetch_register), object, listener, errorListener);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[fetchData] Error: "+ex);
        }
        return true;
    }

    public void createNewShoppingList(String userId, String userName){
        // Build query url
        String url = getResources().getString(R.string.fetch_shoplist)+"/user/"+userId;
        // Create sample data for user
        ArrayList<ShoppingListItem_Query> queryArrayList = new ArrayList<>();
        ShoppingListItem_QueryHead queryHead = new ShoppingListItem_QueryHead();
        ShoppingListItem_Query sampleItem = new ShoppingListItem_Query();
        sampleItem.setName("Bananas");
        sampleItem.setQty("5");
        queryArrayList.add(sampleItem);
        sampleItem = new ShoppingListItem_Query();
        sampleItem.setName("Chicken breast");
        sampleItem.setQty("10");
        queryArrayList.add(sampleItem);
        queryHead.setName(userName+"s new list");
        queryHead.setItems(queryArrayList);
        String request = new Gson().toJson(queryHead);

        try{
            Log.d(getClass().getName(), "Inside createNewShoppingList, request="+request);
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
                    // Get shoppinglist id
                    ShoppingListItem_ReturnHead returnHead = new Gson().fromJson(response.toString(), ShoppingListItem_ReturnHead.class);
                    if(returnHead!=null && returnHead.getData()!=null){
                        // Save id to shared preferences
                        SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("shoppinglistid", returnHead.getData().getId());
                        editor.commit();
                        // Redirect to main
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(this.getClass().getName(), "Error: " + error.getMessage());
                }
            };
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[createNewShoppingList] Error: "+ex);
        }
    }
}
