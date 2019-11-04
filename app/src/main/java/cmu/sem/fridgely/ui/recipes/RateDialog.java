package cmu.sem.fridgely.ui.recipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.fragment.app.DialogFragment;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.ShoppingListItem;
import cmu.sem.fridgely.ui.shoppinglist.ShoppinglistFragment;

public class RateDialog extends DialogFragment {
    private float rating;

    public RateDialog(float rating){
        this.rating = rating;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.dialog_submit_recipe_rate, null);
        final RatingBar rate = viewInflated.findViewById(R.id.recipeRatingBarEdit);
        rate.setRating(rating);
        rate.setMax(5);
        builder.setView(viewInflated);

        builder.setPositiveButton(R.string.universal_submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        System.out.println("[RatingDialog] User submitted rating");
                        if(rate.getRating()!=rating){
                            System.out.println("Update rating to "+rate.getRating());
                            // Call fragment
                            RecipeFragment recipeFragment = (RecipeFragment) getFragmentManager().findFragmentByTag("recipefrag");
                            // TODO: HERE, add link to modify rating score
                        }
                    }
                })
                .setNegativeButton(R.string.universal_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                        System.out.println("[RatingDialog] User canceled rating action");
                    }
                });

        return builder.create();
    }
}
