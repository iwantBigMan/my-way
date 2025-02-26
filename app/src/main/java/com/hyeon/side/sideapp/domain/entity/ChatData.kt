package com.hyeon.side.sideapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatData(
    val name: String? = null,
    val message: String? = null,
    val time: String? = null
) : Parcelable
