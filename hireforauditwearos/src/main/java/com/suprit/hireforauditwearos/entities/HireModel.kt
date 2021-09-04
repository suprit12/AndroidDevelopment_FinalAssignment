package com.suprit.hireaudit.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey

data class HireModel (
        val _id : String? = null,
        val userID : String? = null,
        val accountantID : Accountant? = null,
        val startDate : String? = null,
        val endDate : String? = null
        ) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var hireID : Int = 0

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Accountant::class.java.classLoader),
            parcel.readString(),
            parcel.readString()) {
        hireID = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(userID)
        parcel.writeParcelable(accountantID, flags)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeInt(hireID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HireModel> {
        override fun createFromParcel(parcel: Parcel): HireModel {
            return HireModel(parcel)
        }

        override fun newArray(size: Int): Array<HireModel?> {
            return arrayOfNulls(size)
        }
    }


}