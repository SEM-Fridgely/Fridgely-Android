package cmu.sem.fridgely;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.util.ArrayList;

import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.object.ShoppingListItem;
import cmu.sem.fridgely.ui.recipes.RecipeFragment;
import cmu.sem.fridgely.ui.settings.SettingsFragment;
import cmu.sem.fridgely.ui.searecipes.SeaRecipeFragment;
import cmu.sem.fridgely.ui.shoppinglist.AddItemDialog;
import cmu.sem.fridgely.ui.shoppinglist.ShoppinglistFragment;
import cmu.sem.fridgely.ui.trends.TrendsFragment;

public class MainActivity extends AppCompatActivity
        implements SeaRecipeFragment.OnFragmentInteractionListener {

//    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // Set up NavigationItemSelectedListener to handle action while switching fragments
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Manual close the drawer
                drawer.closeDrawer(GravityCompat.START);
                switch(menuItem.getItemId()){
                    case R.id.nav_shoppinglist:
                        ShoppinglistFragment shoppinglistFragment = new ShoppinglistFragment();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("shoplistfrag")
                                .replace(R.id.nav_host_fragment, shoppinglistFragment, "shoplistfrag")
                                .commit();
                        break;
                    case R.id.nav_recipes:
                        RecipeFragment recipeFragment = new RecipeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("recipefrag")
                                .replace(R.id.nav_host_fragment, recipeFragment, "recipefrag")
                                .commit();
                        break;
                    case R.id.nav_trends:
                        TrendsFragment trendsFragment = new TrendsFragment();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("trendsfrag")
                                .replace(R.id.nav_host_fragment, trendsFragment, "trendsfrag")
                                .commit();
                        break;
                    case R.id.nav_settings:
                        SettingsFragment settingsFragment = new SettingsFragment();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("settingsfrag")
                                .replace(R.id.nav_host_fragment, settingsFragment, "settingsfrag")
                                .commit();
                        break;
                    case R.id.nav_recipes_search:
                        SeaRecipeFragment seaRecipeFragment = new SeaRecipeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack("searchrecipefrag")
                                .replace(R.id.nav_host_fragment, seaRecipeFragment, "searchrecipefrag")
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

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_shoppinglist, R.id.nav_recipes,
//                R.id.nav_trends, R.id.nav_settings)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        // Toggle permission for network connection
        toggleNetworkSecurity();

        // Load sample data
        loadTestData();

        // Load default home fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new ShoppinglistFragment(), "shoplistfrag")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public void showFloatingActionButton(){
        fab.show();

    }

    public void hideFloatingActionButton(){
        fab.hide();
    }

    public void loadTestData(){
        // Initialize shared preference
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Check if the data exists
        if(!sharedPreferences.contains("shoppinglist")){
            // Create sample datasets
            ArrayList<ShoppingListItem> items = new ArrayList<>();
            items.add(new ShoppingListItem("Eggs", 5.0));
            items.add(new ShoppingListItem("Chicken breast", 10.0));

            Gson gson = new Gson();

            editor.putString("shoppinglist", gson.toJson(items));
            editor.commit();
        }
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
}
