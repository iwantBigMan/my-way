package com.hyeon.side.sideapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friends(
    val id: String,
    val nickname: String,
    val email: String,
    val statusMessage: String // 상태 메시지
) : Parcelable