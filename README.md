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
Card card = new Card("411111111111111", "123", 9, 2020, "prueba_android@culqi.com");
Token token = new Token("{LLAVE PUBLICA}");

token.createToken(getApplicationContext(), card, new TokenCallback() {
      @Override
      public void onSuccess(JSONObject token) {
            // token
            token.get("id").toString()
      }

      @Override
      public void onError(Exception error) {
      }
});
```
