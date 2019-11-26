package cmu.sem.fridgely;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cmu.sem.fridgely.object.FilterObject;
import cmu.sem.fridgely.ui.searecipes.SeaRecipeFragment;
import cmu.sem.fridgely.ui.settings.SettingsFragment;
import cmu.sem.fridgely.ui.shoppinglist.AddItemDialog;
import cmu.sem.fridgely.ui.shoppinglist.ShoppinglistFragment;
import cmu.sem.fridgely.ui.trends.TrendsFragment;
import cmu.sem.fridgely.util.TypefaceUtil;

public class MainActivity extends AppCompatActivity
        implements SeaRecipeFragment.OnFragmentInteractionListener {

    private FloatingActionButton fab;
    //private MenuItem searching;
    public ArrayList<FilterObject> filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up font family
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Sansita-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        // !!Danger Zone!!
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //TODO: Account validation handle - must be logged in to reach here

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddItemDialog().show(getSupportFragmentManager(), "newshopitemfrag");
            }
        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Set up header view with user icon and info
        SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
        ImageView userIcon = navigationView.getHeaderView(0).findViewById(R.id.userIcon);
        Glide.with(this).load(getResources().getIdentifier("baker", "drawable", this.getPackageName())).apply(RequestOptions.circleCropTransform()).into(userIcon);
        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        System.out.println("[MainActivity] Get account name="+sharedPreferences.getString("fridgelier", "empty"));
        userName.setText(sharedPreferences.getString("fridgelier", ""));
        TextView signout = navigationView.getHeaderView(0).findViewById(R.id.logout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear shared preference
                SharedPreferences sharedPreferences = getSharedPreferences("fridgely", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", "empty");
                editor.putString("fridgelier", "empty");
                editor.putString("shoppinglistid", "empty");
                editor.commit();
                // Navigate to login page
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set up NavigationItemSelectedListener to handle action while switching fragments
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Manual close the drawer
                drawer.closeDrawer(GravityCompat.START);
                switch(menuItem.getItemId()){
                    case R.id.nav_shoppinglist:
                        fab.show();
                        //searching.setVisible(false);
                        ShoppinglistFragment shoppinglistFragment = new ShoppinglistFragment();
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("shoplistfrag")
                                .replace(R.id.nav_host_fragment, shoppinglistFragment, "shoplistfrag")
                                .commit();
                        break;
                    case R.id.nav_recipes:
                        fab.hide();
                        //searching.setVisible(true);
                        SeaRecipeFragment seaRecipeFragment = new SeaRecipeFragment();
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("searchrecipefrag")
                                .replace(R.id.nav_host_fragment, seaRecipeFragment, "searchrecipefrag")
                                .commit();
                        break;
                    case R.id.nav_trends:
                        fab.hide();
                        //searching.setVisible(false);
                        TrendsFragment trendsFragment = new TrendsFragment();
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("trendsfrag")
                                .replace(R.id.nav_host_fragment, trendsFragment, "trendsfrag")
                                .commit();
                        break;
                    case R.id.nav_settings:
                        fab.hide();
                        //searching.setVisible(false);
                        SettingsFragment settingsFragment = new SettingsFragment();
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("settingsfrag")
                                .replace(R.id.nav_host_fragment, settingsFragment, "settingsfrag")
                                .commit();
                        break;
                }
                return true;
            }
        });

        // Setup menu toggle button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Init search filter
        filters = new ArrayList<>();

        // Toggle permission for network connection
        toggleNetworkSecurity();

        // Load default home fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new ShoppinglistFragment(), "shoplistfrag")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //searching = menu.findItem(R.id.action_search);
        // Hide toolbar search
        //searching.setVisible(false);
        return true;
    }

    public void showFloatingActionButton(){
        fab.show();
    }

    public void hideFloatingActionButton(){
        fab.hide();
    }

    public void toggleNetworkSecurity(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                System.out.println("not getting request!");
            } else {
                System.out.println("Try to approve permission");
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        PackageManager.PERMISSION_GRANTED);
            }
        } else {
            // Permission has already been granted
            System.out.println("Permission granted!!");
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public ArrayList<FilterObject> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<FilterObject> filters) {
        for(FilterObject filter : filters)
            System.out.println("Got "+filter.category+"="+filter.filterTitle);
        this.filters = filters;
    }
}
