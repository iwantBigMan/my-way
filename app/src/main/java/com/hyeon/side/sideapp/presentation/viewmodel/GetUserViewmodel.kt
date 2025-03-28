package com.hyeon.side.sideapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeon.side.sideapp.domain.entity.User
import com.hyeon.side.sideapp.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserViewmodel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> get() = _user

    fun fetchAllUsers() {
        viewModelScope.launch {
            try {
                val users = userUseCase()
                _user.value = users
                Log.d("GetUserViewmodel", "Fetched users: $users")
            } catch (e: Exception) {
                Log.e("GetUserViewmodel", "Error fetching users", e)
            }
        }
    }
}