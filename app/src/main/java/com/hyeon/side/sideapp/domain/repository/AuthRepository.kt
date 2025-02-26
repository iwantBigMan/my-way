package com.hyeon.side.sideapp.domain.repository

interface AuthRepository {
    suspend fun login(id: String, password: String): Boolean
    suspend fun signUp(email: String, password: String, nickname: String, name: String, id: String): Boolean
    suspend fun snsSignUp(nickname: String, name: String): Boolean
}