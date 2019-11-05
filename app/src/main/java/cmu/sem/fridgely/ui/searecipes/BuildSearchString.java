package cmu.sem.fridgely.ui.searecipes;

public class BuildSearchString {
    //TODO: Move API KEY and API ID
//    String testlink = "https://api.edamam.com/search?q=steak&app_id=&app_key=f6329aeb0ce6a806b529977877a9b5a4%20&from=0&to=10&calories=700-800&diet=low-fat";
    private String API_KEY = "f6329aeb0ce6a806b529977877a9b5a4%20";
    private String API_ID = "3ef87764";
    private String query;
    private String URL = "";

    // Minimum query
    public BuildSearchString(String query){
        this.query = query;
    }


    public String getUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.edamam.com/search?app_id=");
        sb.append(API_ID);
        sb.append("&app_key=");
        sb.append(API_KEY);
        sb.append("&q=");
        sb.append(query);

        return sb.toString();
    }
}
