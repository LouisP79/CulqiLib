package com.culqilib.listener;

import com.culqilib.model.Error;

import org.json.JSONObject;

/**
 * Created by culqi on 2/7/17.
 */

public interface TokenCallback {

    void onSuccess(JSONObject token);

    void onError(Error error);

}
