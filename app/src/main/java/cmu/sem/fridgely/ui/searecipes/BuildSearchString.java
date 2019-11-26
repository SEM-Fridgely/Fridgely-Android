package cmu.sem.fridgely.ui.searecipes;

import android.util.Log;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;

import cmu.sem.fridgely.object.FilterObject;

public class BuildSearchString {
    private String query;
    private ArrayList<FilterObject> filter;
    private String URL = "";

    // Minimum query
    public BuildSearchString(String query){
        this.query = query;
    }

    // With filter
    public BuildSearchString(String query, ArrayList<FilterObject> filter){
        this.query = query;
        this.filter = filter;
    }


    public String getUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://52.91.24.198:8080/fridgely/all?");
        sb.append("&q=");
        sb.append(query);
//        try {
//            sb.append(Hex.encodeHex(query.getBytes("UTF-8")));
//        }catch(Exception ex){
//            Log.e(getClass().getName(), "Error converting to hex! "+ex);
//        }

        if(filter!=null && !filter.equals("")){
            for(FilterObject filterObject : filter){
                sb.append("&");
                sb.append(filterObject.getCategory());
                sb.append("=");
                sb.append(filterObject.getFilterTitle());
            }
        }

        return sb.toString();
    }
}
