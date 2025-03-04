package com.hyeon.side.sideapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User( // 사용자 정보
    val id: String,
    val nickname: String,
    val name: String,
    val email: String,
    val status: String
) : Parcelable
