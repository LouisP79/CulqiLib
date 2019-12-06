package com.culqilib.extensions

import com.culqilib.model.Iin
import com.culqilib.model.Issuer
import com.culqilib.model.TokenError
import com.culqilib.model.TokenSuccess
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

fun ResponseBody.successResponse(): TokenSuccess {
    val jsonResponse = JSONObject(string())
    return TokenSuccess(jsonResponse.get("object").toString(),
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
                            jsonResponse.getJSONObject("iin").getJSONObject("issuer").get("country_code").toString()),
                    listInstallmentsAllowed(jsonResponse.getJSONObject("iin").getJSONArray("installments_allowed"))))
}

private fun listInstallmentsAllowed(array: JSONArray): List<Int>{
    val response = mutableListOf<Int>()
    for(i in 0 until array.length()){
        response.add(array.getInt(i))
    }
    return response
}

fun ResponseBody.errorResponse(): TokenError {
    val jsonResponse = JSONObject(string())
    return try {
        TokenError(jsonResponse.get("merchant_message").toString(),
                jsonResponse.get("type").toString(),
                jsonResponse.get("user_message").toString())

    }catch (e: Exception){
        TokenError(jsonResponse.get("merchant_message").toString(),
                jsonResponse.get("type").toString())
    }
}