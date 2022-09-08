package com.culqilib

import com.culqilib.extensions.errorResponse
import com.culqilib.extensions.successResponse
import com.culqilib.extensions.exception
import com.culqilib.listener.TokenCallback
import com.culqilib.restServices.TokenService
import com.culqilib.restServices.deserializer.TokenDeserializer
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
class Token(private var apiKey: String) {


    fun createToken(card: TokenDeserializer.CardRequest, listener: TokenCallback){
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://secure.culqi.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build()


        retrofit.create(TokenService::class.java)
                .token("Bearer $apiKey", card)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        if(t.message!=null)
                            listener.onError(exception(t.message!!))
                        else listener.onError(exception("Unknown Message"))
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) listener.onSuccess(response.body()!!.successResponse())
                        else{
                            when(response.code()){
                                400 -> {listener.onError(exception("Culqi Store Token Error"))}
                                else -> {
                                    if(response.errorBody()?.errorResponse() != null){
                                        listener.onError(response.errorBody()!!.errorResponse())
                                    }else{
                                        listener.onError(exception("Unknown Message"))
                                    }
                                }
                            }
                        }
                    }
                })
    }
}