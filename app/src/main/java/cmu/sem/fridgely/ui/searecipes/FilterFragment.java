package cmu.sem.fridgely.ui.searecipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.FilterObject;

public class FilterFragment extends DialogFragment {

    final ArrayList<FilterObject> filters;
    public String[] diet_array;
    public String[] health_array;
    public String[] cuisine_array;
    private ChipCloud chipCloudDiet;
    private ChipCloud chipCloudHealth;
    private ChipCloud chipCloudCuisine;

    public FilterFragment(ArrayList<FilterObject> filters) {
        this.filters = filters;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.fragment_filter, null);

        // String arrays
        diet_array = getResources().getStringArray(R.array.diet_lowc);
        health_array = getResources().getStringArray(R.array.health_lowc);
        cuisine_array = getResources().getStringArray(R.array.cuisine_lowc);

        chipCloudDiet = (ChipCloud) root.findViewById(R.id.chip_cloud_diet);
        chipCloudDiet.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                filters.add(new FilterObject("diet", diet_array[index]));
            }

            @Override
            public void chipDeselected(int index) {
                filters.remove(new FilterObject("diet", diet_array[index]));
            }
        });

        chipCloudHealth = (ChipCloud) root.findViewById(R.id.chip_cloud_health);
        chipCloudHealth.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                filters.add(new FilterObject("health", health_array[index]));
            }

            @Override
            public void chipDeselected(int index) {
                filters.remove(new FilterObject("health", health_array[index]));
            }
        });

        chipCloudCuisine = (ChipCloud) root.findViewById(R.id.chip_cloud_cuisine);
        chipCloudCuisine.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                filters.add(new FilterObject("cuisine", cuisine_array[index]));
            }

            @Override
            public void chipDeselected(int index) {
                filters.remove(new FilterObject("cuisine", cuisine_array[index]));
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(root);
        builder.setPositiveButton(R.string.item_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).setFilters(filters);
                        dismiss();
                        return;
                    }
                })
                .setNegativeButton(R.string.universal_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().cancel();
                        dismiss();
                        return;
                    }
                });
        checkPreviousChips();
        return builder.create();
    }

    private void checkPreviousChips(){
        if(filters.size()==0) return;
        List<String> _da = Arrays.asList(diet_array);
        List<String> _ha = Arrays.asList(health_array);
        List<String> _ca = Arrays.asList(cuisine_array);

        for(int i=0;i<filters.size();i++){
            FilterObject _filter = filters.get(i);
            if(_filter.getCategory().equals("diet")){
                chipCloudDiet.setSelectedChip(_da.indexOf(_filter.getFilterTitle()));
            }else if(_filter.getCategory().equals("health")){
                chipCloudHealth.setSelectedChip(_ha.indexOf(_filter.getFilterTitle()));
            }else if(_filter.getCategory().equals("cuisine")){
                chipCloudCuisine.setSelectedChip(_ca.indexOf(_filter.getFilterTitle()));
            }
        }
    }
}
