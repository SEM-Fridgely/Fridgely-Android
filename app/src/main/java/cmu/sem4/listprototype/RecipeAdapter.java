package cmu.sem4.listprototype;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

public class RecipeAdapter extends ArrayAdapter<Recipe> implements View.OnClickListener {
    private ArrayList<Recipe> dataSet;
//    Context mContext;
//
//    private static class ViewHolder{
//        TextView name;
//        TextView calorie;
//        RatingBar ratings;
//        TextView raterNum;
//        ImageView preview;
//    }
//
//    public RecipeAdapter(ArrayList<Recipe> data, Context context){
//        super(context, R.layout.activity_main, data);
//        this.dataSet = data;
//        this.mContext=context;
//    }

    public RecipeAdapter(Activity context, ArrayList<Recipe> recipes){
        super(context, 0, recipes);
    }

    @Override
    public void onClick(View v){

    }

    private int lastPosition = -1;

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        Recipe recipe = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder; // view lookup cache stored in tag
//
//        final View result;
//
//        if (convertView == null) {
//
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.list_item, parent, false);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
//            viewHolder.calorie = (TextView) convertView.findViewById(R.id.calorie);
//            viewHolder.ratings = (RatingBar) convertView.findViewById(R.id.rating);
//            viewHolder.raterNum = (TextView) convertView.findViewById(R.id.raterNum);
//            viewHolder.preview = (ImageView) convertView.findViewById(R.id.preview);
//
//            result=convertView;
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//            result=convertView;
//        }
//
//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;
//
//        viewHolder.name.setText(recipe.getLabel());
//        viewHolder.calorie.setText(recipe.getCalories());
//        viewHolder.ratings.setRating(recipe.getRating());
//        viewHolder.raterNum.setText(recipe.getRaterNum());
//        viewHolder.name.setOnClickListener(this);
//        Glide.with(convertView).load(recipe.getImage().get(0)).into(viewHolder.preview);
//        viewHolder.preview.setOnClickListener(this);
//        viewHolder.preview.setTag(position);
//        // Return the completed view to render on screen
//        return convertView;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create new View object
        View listedItemsView = convertView;
        if(listedItemsView==null){
            listedItemsView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Recipe currentRecipe = getItem(position);

        final TextView labelTextView = listedItemsView.findViewById(R.id.name);
        labelTextView.setText(currentRecipe.getLabel());

        final RatingBar ratings = listedItemsView.findViewById(R.id.rating);
        // Random ratings
        Random random = new Random();
        ratings.setRating(random.nextInt(5));
        //ratings.setRating(currentRecipe.getRating());
//        originTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                t2s.speak(originTextView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
//            }
//        });

        final TextView calorie = listedItemsView.findViewById(R.id.calorie);
        calorie.setText(currentRecipe.getCalories().split("\\.")[0]);

        final TextView raterNum = listedItemsView.findViewById(R.id.raterNum);
        //raterNum.setText(currentRecipe.getRaterNum()+"");
        raterNum.setText(random.nextInt(500)+"");

        final ImageView preview = listedItemsView.findViewById(R.id.preview);
        Glide.with(listedItemsView).load(currentRecipe.getImage()).apply(RequestOptions.circleCropTransform()).into(preview);
//        if(preview.hasImage()){
//            iconImage.setImageResource(currentWords.getImageResId());
//        }else{
//            LinearLayout imageLinearLayout = listedItemsView.findViewById(R.id.imgLinearLayout);
//            imageLinearLayout.setVisibility(View.GONE);
//            iconImage.setVisibility(View.GONE);
//        }

//        Random rnd = new Random();
//        listedItemsView.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));

        return listedItemsView;
    }
}
