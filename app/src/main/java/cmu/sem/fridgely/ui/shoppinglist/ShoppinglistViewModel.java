package cmu.sem.fridgely.ui.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cmu.sem.fridgely.MainActivity;
import cmu.sem.fridgely.object.Recipe;
import cmu.sem.fridgely.object.ShoppingListItem;
import cmu.sem.fridgely.object.ShoppingListItem_Query;

public class ShoppinglistViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ShoppingListItem_Query>> mItems;

    public ShoppinglistViewModel() {
        mItems = new MutableLiveData<>();
    }

    public void postItems(ShoppingListItem_Query item){
        ArrayList<ShoppingListItem_Query> oldList = mItems.getValue();
        oldList.add(item);
        mItems.postValue(oldList);
    }

    public void deleteItem(int position){
        ArrayList<ShoppingListItem_Query> oldList = mItems.getValue();
        oldList.remove(position);
        mItems.postValue(oldList);
    }

    public void postAllItems(ArrayList<ShoppingListItem_Query> items){
        mItems.setValue(items);
    }

    public LiveData<ArrayList<ShoppingListItem_Query>> getItems() {
        return mItems;
    }
}