package cmu.sem.fridgely.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import cmu.sem.fridgely.object.Data;

public class HTTPUtil {

    Data user;
    static String ret;

    public static String postDatausingvolley(Context context, String url, String request){
        RequestQueue queue = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        final JSONObject object;
        final JsonObjectRequest jsonObjectRequest;

        try{
            if(request!=null)
                object = new JSONObject(request);
            else
                object = null;
//            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    System.out.println("[RES][POSTVOLLEY] Get response: "+response.toString());
//                    ret = response.toString();
//                }
//            };
//
//            jsonObjectRequest = new JsonObjectRequest(url, object,
//                    listener,
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            System.out.println("[ERR][POSTVOLLEY] Get error: "+error.toString());
//                        }
//                    });
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("[ERR][POSTVOLLEY] Get error: "+error.toString());
                        }
                    };
            jsonObjectRequest = new JsonObjectRequest(url, object, future, errorListener);
            queue.add(jsonObjectRequest);
            return future.get(30, TimeUnit.SECONDS).toString();
        }catch(Exception ex){
            Log.e("postdatausingvolly", "Error: "+ex);
        }

        return ret;
    }
}
