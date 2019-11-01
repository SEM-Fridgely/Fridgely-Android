package cmu.sem.fridgely.ui.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.ShoppingListItem;

public class AddItemDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.dialog_add_shopping_item, null);
        final EditText itemTitle = viewInflated.findViewById(R.id.itemTitleEditText);
        final EditText itemQuant = viewInflated.findViewById(R.id.itemQuantEditText);
        builder.setView(viewInflated);

        builder.setPositiveButton(R.string.item_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        ShoppingListItem item = new ShoppingListItem(itemTitle.getText().toString(), Double.parseDouble(itemQuant.getText().toString()));
                        System.out.println(itemTitle.getText().toString()+", "+itemQuant.getText().toString());
                        // Call fragment
//                        ShoppinglistFragment shoppinglistFragment = (ShoppinglistFragment) ((MainActivity)getActivity()).getSupportFragmentManager().findFragmentById(R.id.nav_shoppinglist);
//                        if(shoppinglistFragment!=null){
//                            shoppinglistFragment.insertNewItem(item);
//                        }
                        // Add to shared preference
                        SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String oldJsonString = sharedPreferences.getString("shoppinglist", "");
                        ArrayList<ShoppingListItem> items = new Gson().fromJson(oldJsonString, new TypeToken<ArrayList<ShoppingListItem>>(){}.getType());
                        items.add(item);
                        // Save edit to sharedpreferences
                        editor.putString("shoppinglist", new Gson().toJson(items));
                        editor.commit();
                        // Refresh list

//                        FragmentManager fragmentManager = ((MainActivity)getActivity()).getSupportFragmentManager();
//                        ShoppinglistFragment shoppinglistFragment = (ShoppinglistFragment) fragmentManagergetChildFragmentManager().getFragments().get(0);
//                        shoppinglistFragment.insertNewItem();
                    }
                })
                .setNegativeButton(R.string.item_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
