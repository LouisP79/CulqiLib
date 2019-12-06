package com.culqilib.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Louis Perdomo -> louis.perdomo79@gmail.com on 24/06/2019.
 */
data class Iin(val objectIin: String,
          val bin: String,
          val cardBrand: String,
          val cardType: String,
          val cardCategory: String,
          val issuer: Issuer,
          val installmentsAllowed: List<Int>): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readParcelable(Issuer::class.java.classLoader)!!,
            parcel.createIntArray()!!.toList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(objectIin)
        parcel.writeString(bin)
        parcel.writeString(cardBrand)
        parcel.writeString(cardType)
        parcel.writeString(cardCategory)
        parcel.writeParcelable(issuer, flags)
        parcel.writeIntArray(installmentsAllowed.toIntArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Iin> {
        override fun createFromParcel(parcel: Parcel): Iin {
            return Iin(parcel)
        }

        override fun newArray(size: Int): Array<Iin?> {
            return arrayOfNulls(size)
        }
    }
}