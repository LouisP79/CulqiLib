package com.culqilib.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
data class TokenError(val merchantMessage: String,
                 val userMessage: String,
                 val type: String): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(merchantMessage)
        parcel.writeString(userMessage)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TokenError> {
        override fun createFromParcel(parcel: Parcel): TokenError {
            return TokenError(parcel)
        }

        override fun newArray(size: Int): Array<TokenError?> {
            return arrayOfNulls(size)
        }
    }


}