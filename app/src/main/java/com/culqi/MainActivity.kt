package com.culqi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.culqilib.Token
import com.culqilib.listener.TokenCallback
import com.culqilib.model.TokenError
import com.culqilib.model.TokenSuccess
import com.culqilib.restServices.deserializer.TokenDeserializer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card = TokenDeserializer.CardRequest(cardNumber="4111111111111111",
                                                    cvv="123",
                                                    expirationMonth=9,
                                                    expirationYear=2020,
                                                    email="annonimo79@gmail.com")

        val token = Token("pk_test_Jf2vS2zIlimqIgm8")
        token.createToken(card, object : TokenCallback {
            override fun onSuccess(token: TokenSuccess) {
                textView.text = token.id

                textView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            override fun onError(tokenError: TokenError) {
                textView.text = if (tokenError.userMessage.isNotEmpty()) tokenError.userMessage else tokenError.merchantMessage
                Log.e("MerchantMessage", tokenError.merchantMessage)
                Log.e("UserMessage", tokenError.userMessage)
                Log.e("Type", tokenError.type)

                textView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }
}
