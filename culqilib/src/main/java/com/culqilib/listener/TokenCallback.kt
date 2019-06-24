package com.culqilib.listener


import com.culqilib.model.TokenSuccess
import com.culqilib.model.TokenError

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
interface TokenCallback {

    fun onSuccess(token: TokenSuccess)

    fun onError(tokenError: TokenError)

}
