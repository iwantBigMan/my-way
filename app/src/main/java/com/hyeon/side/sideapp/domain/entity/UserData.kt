package com.hyeon.side.sideapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(val name : String,
                      val id: String,
                      val password: String,
                      val nickname: String,
    val email: String)  : Parcelable
