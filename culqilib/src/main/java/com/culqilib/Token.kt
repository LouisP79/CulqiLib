package com.culqilib

import com.culqilib.extensions.errorResponse
import com.culqilib.extensions.successResponse
import com.culqilib.listener.TokenCallback
import com.culqilib.restServices.TokenService
import com.culqilib.restServices.deserializer.TokenDeserializer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                .enqueue(object: Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {t.printStackTrace()}
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(response.isSuccessful) listener.onSuccess(response.body()!!.successResponse())
                        else listener.onError(response.errorBody()!!.errorResponse())
                    }
                })
    }
}