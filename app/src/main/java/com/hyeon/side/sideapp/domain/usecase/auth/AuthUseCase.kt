package com.hyeon.side.sideapp.domain.usecase.auth

import com.hyeon.side.sideapp.domain.repository.AuthRepository

class AuthUseCase(private val authRepository: AuthRepository) {
    suspend fun login(id: String, password: String): Boolean {
        return authRepository.login(id, password)
    }

    suspend fun signUp(email: String, password: String, nickname: String, name: String, id: String): Boolean {
        return authRepository.signUp(email, password, nickname, name, id)
    }

    suspend fun snsSignUp(nickname: String, name: String): Boolean {
        return authRepository.snsSignUp(nickname, name)
    }
}