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
