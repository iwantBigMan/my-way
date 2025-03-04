package com.hyeon.side.sideapp.domain.repository

import com.hyeon.side.sideapp.domain.entity.User

interface UserRepository {
    suspend fun getUserData(userId: String): List<User>
}