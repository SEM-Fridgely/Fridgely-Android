package cmu.sem.fridgely.ui.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.ShoppingListItem;
import cmu.sem.fridgely.object.ShoppingListItem_Query;
import cmu.sem.fridgely.object.ShoppingListItem_Return;

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
                        if(!itemTitle.getText().toString().equals("") && !itemQuant.getText().toString().equals("")){
                            ShoppingListItem_Query query = new ShoppingListItem_Query();
                            query.setName(itemTitle.getText().toString());
                            query.setQty(itemQuant.getText().toString());

                            System.out.println(itemTitle.getText().toString()+", "+itemQuant.getText().toString());
                            // Call fragment
                            ShoppinglistFragment shoppinglistFragment = (ShoppinglistFragment) getFragmentManager().findFragmentByTag("shoplistfrag");
                            if(shoppinglistFragment!=null){
                                System.out.println("got!!!");
                                shoppinglistFragment.insertNewItem(query);
                            }
                        }else{
                            Log.d(getTag(), "Got empty input from float action button dialog.");
                            Toast.makeText(getContext(), "Add item cannot be empty!", Toast.LENGTH_SHORT).show();
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
