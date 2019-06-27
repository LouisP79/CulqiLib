package com.culqilib

import android.util.Log
import com.culqilib.listener.TokenCallback
import com.culqilib.model.*
import com.culqilib.model.TokenSuccess
import com.culqilib.restServices.TokenService
import com.culqilib.restServices.deserializer.TokenDeserializer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
class Token(private var apiKey: String) {

    fun createToken(card: TokenDeserializer.CardRequest, listener: TokenCallback){
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://secure.culqi.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit.create(TokenService::class.java)
                .token("Bearer $apiKey",card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                    onError = { Log.e("CULQI ERROR", it.message) },
                    onComplete = { println("onComplete!") },
                        onNext = { if(it.isSuccessful){
                                      val jsonResponse = JSONObject(it.body()!!.string())
                                      val tokenSuccess = TokenSuccess(jsonResponse.get("object").toString(),
                                              jsonResponse.get("id").toString(),
                                              jsonResponse.get("type").toString(),
                                              jsonResponse.get("creation_date").toString().toLong(),
                                              jsonResponse.get("email").toString(),
                                              jsonResponse.get("card_number").toString(),
                                              jsonResponse.get("last_four").toString(),
                                              jsonResponse.get("active").toString().toBoolean(),
                                              Iin(jsonResponse.getJSONObject("iin").get("object").toString(),
                                                      jsonResponse.getJSONObject("iin").get("bin").toString(),
                                                      jsonResponse.getJSONObject("iin").get("card_brand").toString(),
                                                      jsonResponse.getJSONObject("iin").get("card_type").toString(),
                                                      jsonResponse.getJSONObject("iin").get("card_category").toString(),
                                                      Issuer(jsonResponse.getJSONObject("iin").getJSONObject("issuer").get("name").toString(),
                                                              jsonResponse.getJSONObject("iin").getJSONObject("issuer").get("country").toString(),
                                                              jsonResponse.getJSONObject("iin").getJSONObject("issuer").get("country_code").toString())))
                                      listener.onSuccess(tokenSuccess)
                                  }else{
                                      val jsonResponse = JSONObject(it.errorBody()!!.string())
                                      val tokenError = TokenError(jsonResponse.get("merchant_message").toString(),
                                              jsonResponse.get("user_message").toString(),
                                              jsonResponse.get("type").toString())
                                      listener.onError(tokenError)
                                  } }
                )
    }
}