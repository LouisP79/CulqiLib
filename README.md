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
  implementation 'com.github.LouisP79:CulqiLib:v2.2'
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
    public void onError(Error error) {
        Log.e("UserMessage",error.getUserMessage()));
        Log.e("Code",error.getCode());
        Log.e("MerchantMessage",error.getMerchantMessage());
        Log.e("Object",error.getObject());
        Log.e("Param",error.getParam());
        Log.e("Type",error.getType());
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


#### Respuesta del método onError(Error error)
```java
public class Error implements Parcelable {

    private String code;
    private String merchantMessage;
    private String userMessage;
    private String type;
    private String param;
    private String object;

    public Error() {
    }

    public Error(String code, String merchantMessage, String userMessage, String type, String param, String object) {
        this.code = code;
        this.merchantMessage = merchantMessage;
        this.userMessage = userMessage;
        this.type = type;
        this.param = param;
        this.object = object;
    }

    private Error(Parcel in) {
        code = in.readString();
        merchantMessage = in.readString();
        userMessage = in.readString();
        type = in.readString();
        param = in.readString();
        object = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(merchantMessage);
        dest.writeString(userMessage);
        dest.writeString(type);
        dest.writeString(param);
        dest.writeString(object);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Error> CREATOR = new Creator<Error>() {
        @Override
        public Error createFromParcel(Parcel in) {
            return new Error(in);
        }

        @Override
        public Error[] newArray(int size) {
            return new Error[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMerchantMessage() {
        return merchantMessage;
    }

    public void setMerchantMessage(String merchantMessage) {
        this.merchantMessage = merchantMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
```
