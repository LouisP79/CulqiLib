package com.culqilib

import android.util.Log
import com.culqilib.listener.TokenCallback
import com.culqilib.model.*
import com.culqilib.model.TokenSuccess
import com.culqilib.restServices.TokenService
import com.culqilib.restServices.deserializer.TokenDeserializer

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
class Token(private var apiKey: String) {

    fun createToken(card: TokenDeserializer.CardRequest, listener: TokenCallback){
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.culqi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        retrofit.create(TokenService::class.java)
                .token("Bearer $apiKey",card)
                .enqueue(object: Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    val jsonResponse = JSONObject(response.body()!!.string())
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
                    val jsonResponse = JSONObject(response.errorBody()!!.string())
                    val tokenError = TokenError(jsonResponse.get("merchant_message").toString(),
                                                jsonResponse.get("user_message").toString(),
                                                jsonResponse.get("type").toString())
                    listener.onError(tokenError)
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {Log.e("CULQI ERROR", t.message)}
        })
    }
}