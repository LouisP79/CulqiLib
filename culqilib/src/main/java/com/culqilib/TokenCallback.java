package com.culqilib;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by culqi on 2/7/17.
 */

public interface TokenCallback {

    void onSuccess(JSONObject token);

    void onError(VolleyError error);

}
