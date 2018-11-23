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
  implementation 'com.github.LouisP79:CulqiLib:V2.1'
```

#### Uso
```java
import com.android.volley.VolleyError;
import com.culqilib.Card;
import com.culqilib.Token;
import com.culqilib.TokenCallback;

...


Card card = new Card("411111111111111", "123", 9, 2020, "prueba_android@culqi.com");
Token token = new Token("{LLAVE PUBLICA}");

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
```

#### Respuesta del método onSuccess(JSONObject token)
```json
{
    "object": "token",
    "id": "tkn_test_6tMWw62MYHvlv3qK",
    "type": "card",
    "creation_date": 1542999157000,
    "email": "annonimo79@gmail.com",
    "card_number": "411111******1111",
    "last_four": "1111",
    "active": true,
    "iin": {
        "object": "iin",
        "bin": "411111",
        "card_brand": "Visa",
        "card_type": "credito",
        "card_category": "Clásica",
        "issuer": {
            "name": "BBVA",
            "country": "PERU",
            "country_code": "PE",
            "website": null,
            "phone_number": null
        },
        "installments_allowed": [
            2,
            4,
            6,
            8,
            10,
            12,
            3,
            5,
            7,
            9,
            24,
            48
        ]
    },
    "client": {
        "ip": "181.65.242.132",
        "ip_country": "Perú",
        "ip_country_code": "PE",
        "browser": null,
        "device_fingerprint": null,
        "device_type": "Escritorio"
    },
    "metadata": {}
}
```


#### Respuesta del método onError(VolleyError error) dentro de error.networkResponse.data
```json
{
    "object": "error",
    "type": "parameter_error",
    "code": "invalid_number",
    "merchant_message": "El número de la tarjeta no es un número de tarjeta de crédito o débito válido. Tiene que ser numérico de 13 a 16 digitos.",
    "user_message": "El numero de tarjeta de crédito o débito brindado no es válido.",
    "param": "card_number"
}
```
