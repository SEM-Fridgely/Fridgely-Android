package cmu.sem.fridgely.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmu.sem.fridgely.R;

public class IngrdientAdapter extends ArrayAdapter<String> {
    private int resourceLayout;
    private Context mContext;

    public IngrdientAdapter(Context context, int resource, ArrayList<String> ingredients) {
        super(context, resource, ingredients);
//        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.ingredient_item, null);
        }

        String ingr = getItem(position);

        TextView ingredientName = v.findViewById(R.id.ingredient_name);
        ingredientName.setText(ingr);

        return v;
    }
}
