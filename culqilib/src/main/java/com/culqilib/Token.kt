package com.culqilib

import android.annotation.SuppressLint
import com.culqilib.extensions.errorResponse
import com.culqilib.extensions.successResponse
import com.culqilib.listener.TokenCallback
import com.culqilib.restServices.TokenService
import com.culqilib.restServices.deserializer.TokenDeserializer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
class Token(private var apiKey: String) {

    @SuppressLint("CheckResult")
    fun createToken(card: TokenDeserializer.CardRequest, listener: TokenCallback){
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://secure.culqi.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        retrofit.create(TokenService::class.java)
                .token("Bearer $apiKey",card)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {  if(it.isSuccessful) listener.onSuccess(it.body()!!.successResponse())
                                    else listener.onError(it.errorBody()!!.errorResponse())},
                        onError = { it.printStackTrace() },
                        onComplete = { println("onComplete!") }
                )
    }
}