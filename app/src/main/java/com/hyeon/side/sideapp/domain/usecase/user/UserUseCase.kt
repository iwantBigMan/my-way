package com.hyeon.side.sideapp.domain.usecase.user

import com.hyeon.side.sideapp.domain.entity.User
import com.hyeon.side.sideapp.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return repository.getAllUsers()
    }
}