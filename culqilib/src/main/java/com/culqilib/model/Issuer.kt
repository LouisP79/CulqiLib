package com.culqilib.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
data class Issuer (val name: String = "",
              val country: String = "",
              val countryCode: String = ""): Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(country)
        parcel.writeString(countryCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Issuer> {
        override fun createFromParcel(parcel: Parcel): Issuer {
            return Issuer(parcel)
        }

        override fun newArray(size: Int): Array<Issuer?> {
            return arrayOfNulls(size)
        }
    }
}