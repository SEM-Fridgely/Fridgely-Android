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
                        if(itemTitle.getText().toString().equals("")){

                        }else{
                            ShoppingListItem item = new ShoppingListItem(itemTitle.getText().toString(), Double.parseDouble(itemQuant.getText().toString()));
                            System.out.println(itemTitle.getText().toString()+", "+itemQuant.getText().toString());
                            // Call fragment
                            ShoppinglistFragment shoppinglistFragment = (ShoppinglistFragment) getFragmentManager().findFragmentByTag("shoplistfrag");
                            if(shoppinglistFragment!=null){
                                System.out.println("got!!!");
                            }
//                            else{
//                                System.out.println("fragments size="+getFragmentManager().getFragments().size());
//                                System.out.println(getFragmentManager().getFragments().get(0).getId());
//                            }
//                            ShoppinglistFragment shoppinglistFragment = (ShoppinglistFragment) getFragmentManager().getFragments().get(1);
                            shoppinglistFragment.insertNewItem(item);

//                            // Add to shared preference
//                            //TODO: Check duplication while adding items
//                            SharedPreferences sharedPreferences = ((MainActivity)getActivity()).getPreferences(Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            String oldJsonString = sharedPreferences.getString("shoppinglist", "");
//                            ArrayList<ShoppingListItem> items = new Gson().fromJson(oldJsonString, new TypeToken<ArrayList<ShoppingListItem>>(){}.getType());
//                            items.add(item);
//                            // Save edit to sharedpreferences
//                            editor.putString("shoppinglist", new Gson().toJson(items));
//                            editor.commit();
                        }
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
}
