package com.suprit.hireaudit.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    var userFName : String? = null,
    var userLName : String? = null,
    var userEmail : String? = null,
    var username : String? = null,
    var gender : String? = null,
    var userPassword : String? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var userID : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        userID = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userFName)
        parcel.writeString(userLName)
        parcel.writeString(userEmail)
        parcel.writeString(username)
        parcel.writeString(gender)
        parcel.writeString(userPassword)
        parcel.writeInt(userID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}