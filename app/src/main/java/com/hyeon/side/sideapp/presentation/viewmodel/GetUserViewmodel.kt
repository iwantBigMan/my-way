package com.hyeon.side.sideapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeon.side.sideapp.domain.entity.User
import com.hyeon.side.sideapp.domain.repository.UserRepository
import com.hyeon.side.sideapp.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUserViewmodel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
  private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> get() = _user

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            val user = userUseCase(userId)
            _user.value = user
        }
    }
}