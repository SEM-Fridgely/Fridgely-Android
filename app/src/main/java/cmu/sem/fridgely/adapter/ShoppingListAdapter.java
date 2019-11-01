package cmu.sem.fridgely.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cmu.sem.fridgely.R;
import cmu.sem.fridgely.object.ShoppingListItem;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> implements View.OnClickListener {
    private ArrayList<ShoppingListItem> items;

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder{
        public CheckBox boughtCheckbox;
        public TextView shopItemName;
        public TextView quantityNumber;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            boughtCheckbox = itemView.findViewById(R.id.bought_checkbox);
            shopItemName = itemView.findViewById(R.id.shop_item_name);
            quantityNumber = itemView.findViewById(R.id.quantity_number);
        }
    }

    public ShoppingListAdapter(ArrayList<ShoppingListItem> items){
        this.items = items;
    }

    @Override
    public void onClick(View v) {
        //TODO: trigger edit dialog
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
        ShoppingListViewHolder shoppingListViewHolder = new ShoppingListViewHolder(v);
        return shoppingListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        holder.shopItemName.setText(items.get(position).getItemTitle());
        holder.quantityNumber.setText(items.get(position).getQuantity()+"");
        holder.boughtCheckbox.setChecked(items.get(position).isBought());
        holder.boughtCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
