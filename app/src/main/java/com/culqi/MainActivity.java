package com.culqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.culqilib.model.Card;
import com.culqilib.Token;
import com.culqilib.listener.TokenCallback;
import com.culqilib.model.Error;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.text_view);

        Card card = new Card("4111111111111111","123",9,2020,"annonimo79@gmail.com");

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
            public void onError(Error error) {
                textView.setText(error.getUserMessage());
            }
        });
    }
}
