package cmu.sem.fridgely.ui.searecipes;

import android.util.Log;

import org.apache.commons.codec.binary.Hex;

public class BuildSearchString {
    //TODO: Move API KEY and API ID

//    String testlink = "https://api.edamam.com/search?q=steak&app_id=&app_key=f6329aeb0ce6a806b529977877a9b5a4%20&from=0&to=10&calories=700-800&diet=low-fat";
    // String http://52.91.24.198:8080/fridgely/all?q=chicken
    private String query;
    private String filter;
    private String URL = "";

    // Minimum query
    public BuildSearchString(String query){
        this.query = query;
    }

    // With filter
    public BuildSearchString(String query, String filter){
        this.query = query;
        this.filter = filter;
    }


    public String getUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("http://52.91.24.198:8080/fridgely/all?");
        sb.append("&q=");
        try {
            sb.append(Hex.encodeHex(query.getBytes("UTF-8")));
        }catch(Exception ex){
            Log.e(getClass().getName(), "Error converting to hex! "+ex);
        }

        if(filter!=null && !filter.equals("")){
            sb.append("&diet-filter=");
            sb.append(filter);
        }

        return sb.toString();
    }
}
