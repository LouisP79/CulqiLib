package com.culqilib.restServices

import com.culqilib.restServices.deserializer.TokenDeserializer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
interface TokenService {

    @POST("/v2/tokens/")
    fun token(@Header("Authorization") culqiKey: String,
            @Body request:TokenDeserializer.CardRequest): Call<ResponseBody>

}