package cmu.sem.fridgely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import cmu.sem.fridgely.object.ShoppingListItem_Return;
import cmu.sem.fridgely.object.ShoppingListItem_ReturnHeadCollection;
import cmu.sem.fridgely.object.User;
import cmu.sem.fridgely.object.UserLogin;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginBtn;
    private TextView register;
    private String jsonRequest;
    private TextView loginfail;
    private ProgressBar loginloading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // !!Danger Zone!!
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // TODO: Check from sharedpreference to see if the application was previously logged in...
        if(chkPreviousLogin()){
            System.out.println("[LoginActivity] Got user, redirecting to main");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        final ImageView loginlogo = findViewById(R.id.login_logo);
        final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        anim.setInterpolator(new BounceInterpolator(0.5, 15));
        loginlogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loginlogo.startAnimation(anim);
            }
        });

        loginloading = findViewById(R.id.loginloading);
        loginloading.setVisibility(View.INVISIBLE);
        loginfail = findViewById(R.id.loginfail);
        loginfail.setVisibility(View.INVISIBLE);

        loginBtn = findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change View
                loginLoadingView();
                // Fetch user input
                loginfail.setVisibility(View.INVISIBLE);
                email = (EditText) findViewById(R.id.email_user_input);
                password = (EditText) findViewById(R.id.password_user_input);
                String email_input = email.getText().toString();
                String password_input = password.getText().toString();
                System.out.println("[LoginActivity] Got user input: "+email_input+", "+password_input);
                // TODO: Security - salt or hash password before transferring to server
                // TODO: Error handling - error while server cannot be reached
                // Validate
                accountValidate(email_input, email_input, password_input);
            }
        });

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean chkPreviousLogin(){
        // Get shared Preference and login history
        SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString("userId", "empty").matches("empty")
                && !sharedPreferences.getString("fridgelier", "empty") .matches("empty")){
            System.out.println("[LoginActivity] User info: "+sharedPreferences.getString("fridgelier", "empty"));
            return true;
        }else{
            return false;
        }
    }

    public boolean accountValidate(String usrName, String usrMail, String pwd){
        // Build request json
        Gson gson = new Gson();
        UserLogin userLogin = new UserLogin(usrMail, pwd);
        jsonRequest = gson.toJson(userLogin);

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
                            loginfail.setVisibility(View.VISIBLE);
                            loginNormalView();
                            return;
                        }
                        // Save to sharedpreference
                        SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", user.getData().id);
                        editor.putString("fridgelier", user.getData().getUsername());
                        editor.commit();

                        // Build object for fetching shopping list
                        ShoppingListItem_QueryHead queryHead = new ShoppingListItem_QueryHead();
                        ArrayList<ShoppingListItem_Query> queryArrayList = new ArrayList<ShoppingListItem_Query>();
                        ShoppingListItem_Query query = new ShoppingListItem_Query();
                        queryHead.setName(user.getData().getUsername()+" list");
                        query.setId("I123");// TODO: Change item id
                        query.setName("Egg");
                        query.setQty("12");
                        query.setUnit("");
                        queryArrayList.add(query);
                        queryHead.setItems(queryArrayList);

                        // Retrieve shopping lists
                        fetchShoppingList(getResources().getString(R.string.fetch_shoplist)+"/user/"+user.getData().getId()+"/all",
                                null);

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
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getResources().getString(R.string.fetch_login), object, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[fetchData] Error: "+ex);
        }
        return true;
    }

    public boolean fetchShoppingList(String url, ShoppingListItem_QueryHead request){
        // Build request json
        String jsonRequest = new Gson().toJson(request);

        try{
            Log.d(getClass().getName(), "Inside fetchShppingList, request="+jsonRequest);
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
                        ShoppingListItem_ReturnHeadCollection returnHeadCollection = new Gson().fromJson(response.toString(), ShoppingListItem_ReturnHeadCollection.class);
                        ShoppingListItem_Return returnHead = returnHeadCollection.getData().get(0);
                        if(returnHead==null){
                            Log.d(getClass().getName(), "Got null response fetching shopping list. Ignore this if its an update or get action.");
                            return;
                        }

                        // Save to sharedpreference
                        SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("shoppinglist", new Gson().toJson(returnHead));
                        editor.putString("shoppinglistid", returnHead.getId());
                        editor.commit();

                        // Start MainActivity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
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
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsonObjectRequest);
        }catch(Exception ex){
            Log.e(getClass().getName(), "[fetchShopList] Error: "+ex.getMessage());
        }
        return true;
    }

    public void loginNormalView(){
        loginBtn.setVisibility(View.VISIBLE);
        loginloading.setVisibility(View.INVISIBLE);
    }

    public void loginLoadingView(){
        loginBtn.setVisibility(View.INVISIBLE);
        loginloading.setVisibility(View.VISIBLE);
    }
}
