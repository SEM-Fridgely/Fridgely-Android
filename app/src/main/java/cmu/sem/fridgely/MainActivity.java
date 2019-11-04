package cmu.sem.fridgely;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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

import cmu.sem.fridgely.object.ShoppingListItem;
import cmu.sem.fridgely.ui.searecipes.SeaRecipeFragment;
import cmu.sem.fridgely.ui.shoppinglist.AddItemDialog;

public class MainActivity extends AppCompatActivity
        implements SeaRecipeFragment.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddItemDialog().show(getSupportFragmentManager(), "add item");
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_shoppinglist, R.id.nav_recipes,
                R.id.nav_trends, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

        // Load sample data
        loadTestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

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

        // Create sample datasets
        ArrayList<ShoppingListItem> items = new ArrayList<>();
        items.add(new ShoppingListItem("Eggs", 5.0));
        items.add(new ShoppingListItem("Chicken breast", 10.0));

        Gson gson = new Gson();

        editor.putString("shoppinglist", gson.toJson(items));
        editor.commit();
    }
    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
