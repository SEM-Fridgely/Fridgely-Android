package cmu.sem.fridgely.ui.recipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cmu.sem.fridgely.object.Recipe;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<Recipe> mList;

    public RecipeViewModel() {
        mList = new MutableLiveData<>();
    }

    public LiveData<Recipe> getList(){
        return mList;
    }
}