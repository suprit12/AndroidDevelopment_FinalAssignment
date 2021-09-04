package com.suprit.hireaudit.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Accountants(
        var _id : String? = null,
        val accountantFName : String? = null,
        val accountantLName : String? = null,
        val accountantEmailAddress : String? = null,
        val gender : String? = null,
        val accountantExperience : String? = null,
        val accountantMob : String? = null,
        // val accountantImage : String? = null,
        val pricePerDay : String? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var accountantID : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        accountantID = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(accountantFName)
        parcel.writeString(accountantLName)
        parcel.writeString(accountantEmailAddress)
        parcel.writeString(gender)
        parcel.writeString(accountantExperience)
        parcel.writeString(accountantMob)
        parcel.writeString(pricePerDay)
        parcel.writeInt(accountantID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Accountants> {
        override fun createFromParcel(parcel: Parcel): Accountants {
            return Accountants(parcel)
        }

        override fun newArray(size: Int): Array<Accountants?> {
            return arrayOfNulls(size)
        }
    }


}

