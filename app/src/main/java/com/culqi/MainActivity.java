package com.culqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.culqilib.Card;
import com.culqilib.Token;
import com.culqilib.TokenCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.text_view);

        Card card = new Card("411111111111111","123",9,2020,"annonimo79@gmail.com");

        Token token = new Token("pk_test_Jf2vS2zIlimqIgm8");

        token.createToken(getApplicationContext(), card, new TokenCallback() {
            @Override
            public void onSuccess(JSONObject token) {
                try {
                    textView.setText(token.get("id").toString());
                } catch (Exception ex){
                    textView.setText(ex.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                try {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(new String(error.networkResponse.data,"UTF-8"));
                    } catch (JSONException e) {
                        textView.setText(e.getMessage());
                    }
                    try {
                        textView.setText(Objects.requireNonNull(jsonObject).get("user_message").toString());
                    } catch (JSONException e) {
                        textView.setText(e.getMessage());
                    }
                } catch (UnsupportedEncodingException e) {
                    textView.setText(e.getMessage());
                }
            }
        });
    }
}
