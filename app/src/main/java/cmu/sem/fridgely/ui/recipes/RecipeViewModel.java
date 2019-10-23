package cmu.sem.fridgely.ui.recipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cmu.sem.fridgely.object.Recipe;

public class RecipeViewModel extends ViewModel {

//    private MutableLiveData<String> mText;
    private MutableLiveData<Recipe> mList;

    public RecipeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is gallery fragment");
        mList = new MutableLiveData<>();
    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    public LiveData<Recipe> getList(){
        return mList;
    }
}