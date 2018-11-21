package com.culqilib;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by culqi on 1/19/17.
 */

public class Token {

    private Config config = new Config();

    private static final String URL = "/tokens/";

    private String api_key;


    public Token(String api_key){
        this.api_key = api_key;
    }

    public void createToken(Context context, Card card, final TokenCallback listener) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("card_number", card.getCard_number());
            jsonBody.put("cvv", card.getCvv());
            jsonBody.put("expiration_month", card.getExpiration_month());
            jsonBody.put("expiration_year", card.getExpiration_year());
            jsonBody.put("email", card.getEmail());
        } catch (Exception ex){
            Log.v("", "ERROR: "+ex.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                config.url_base + URL,
                jsonBody,
                new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                    listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + Token.this.api_key);
                return headers;
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);

    }

}
