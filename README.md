# CulqiLib
Libreria de la implementación de Culqi (Sistema de Pago) para Android

[![](https://jitpack.io/v/LouisP79/CulqiLib.svg)](https://jitpack.io/#LouisP79/CulqiLib)

![](culqi_logo.png)

### Implementación

#### build.gradle (Project)
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
#### build.gradle (Module:app)
```gradle
  implementation 'com.github.LouisP79:CulqiLib:v3.0'
```

#### Uso
```kotlin
import com.culqilib.Token
import com.culqilib.listener.TokenCallback
import com.culqilib.model.TokenError
import com.culqilib.restServices.deserializer.TokenDeserializer
import kotlinx.android.synthetic.main.activity_main.*

...


val card = TokenDeserializer.CardRequest(cardNumber="4111111111111111",
                                                    cvv="123",
                                                    expirationMonth=9,
                                                    expirationYear=2020,
                                                    email="annonimo79@gmail.com")

        val token = Token("pk_test_Jf2vS2zIlimqIgm8")
        token.createToken(card, object : TokenCallback {
            override fun onSuccess(token: com.culqilib.model.Token) {
                textView.text = token.id

                textView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            override fun onError(tokenError: TokenError) {
                textView.text = tokenError.userMessage
                Log.e("MerchantMessage", tokenError.merchantMessage)
                Log.e("Type", tokenError.type)

                textView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
```

#### Respuesta del método onSuccess(token: com.culqilib.model.Token)
```kotlin
data class Token(val objectToken: String,
            val id: String,
            val type: String,
            val creationDate: Long,
            val email: String,
            val cardNumber: String,
            val lastFour: String,
            val active: Boolean,
            val iin: Iin)
```


#### Respuesta del método onError(error: TokenError)
```kotlin
data class TokenError(val merchantMessage: String,
                 val userMessage: String,
                 val type: String)
```
