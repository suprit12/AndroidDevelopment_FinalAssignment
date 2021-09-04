package com.suprit.hireaudit.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Accountant (
        val _id : String? = null,
        val accountantFName : String? = null,
        val accountantLName : String? = null,
        val accountantEmailAddress : String? = null,
        val gender : String? = null,
        val accountantExperience : Int? = null,
        val accountantMob : Int? = null,
       // val accountantImage : String? = null,
        val pricePerDay : Double? = null,

        val accountantImage : String? = null
        ) : Parcelable
{
        @PrimaryKey(autoGenerate = true)
        var accountantID : Int = 0

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readString()) {
                accountantID = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(_id)
                parcel.writeString(accountantFName)
                parcel.writeString(accountantLName)
                parcel.writeString(accountantEmailAddress)
                parcel.writeString(gender)
                parcel.writeValue(accountantExperience)
                parcel.writeValue(accountantMob)
                parcel.writeValue(pricePerDay)
                parcel.writeString(accountantImage)
                parcel.writeInt(accountantID)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Accountant> {
                override fun createFromParcel(parcel: Parcel): Accountant {
                        return Accountant(parcel)
                }

                override fun newArray(size: Int): Array<Accountant?> {
                        return arrayOfNulls(size)
                }
        }


}
