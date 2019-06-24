package com.culqilib.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
data class TokenSuccess(val objectToken: String,
                        val id: String,
                        val type: String,
                        val creationDate: Long,
                        val email: String,
                        val cardNumber: String,
                        val lastFour: String,
                        val active: Boolean,
                        val iin: Iin): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readLong(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readByte() != 0.toByte(),
            parcel.readParcelable(Iin::class.java.classLoader)!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(objectToken)
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeLong(creationDate)
        parcel.writeString(email)
        parcel.writeString(cardNumber)
        parcel.writeString(lastFour)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeParcelable(iin, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TokenSuccess> {
        override fun createFromParcel(parcel: Parcel): TokenSuccess {
            return TokenSuccess(parcel)
        }

        override fun newArray(size: Int): Array<TokenSuccess?> {
            return arrayOfNulls(size)
        }
    }


}