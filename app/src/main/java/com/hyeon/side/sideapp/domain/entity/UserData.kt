package com.hyeon.side.sideapp.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class UserData(val name : String,
                      val id: String,
                      val password: String,
                      val nickname: String,
    val email: String)  : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    constructor() : this("", "", "", "", "")
    fun isValidPassword() = password.length >= 8
    fun isValidNickname() = nickname.length >= 2
    fun isValidId() = id.length >= 4
    fun isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(password)
        parcel.writeString(nickname)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}
