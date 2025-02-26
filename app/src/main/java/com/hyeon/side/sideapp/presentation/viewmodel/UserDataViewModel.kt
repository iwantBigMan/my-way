package com.hyeon.side.sideapp.presentation.viewmodel

import androidx.lifecycle.*
import com.hyeon.side.sideapp.domain.entity.UserData
import com.hyeon.side.sideapp.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _signInResult = MutableLiveData<Boolean>()
    val signInResult: LiveData<Boolean> = _signInResult

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> = _signUpResult

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInResult.value = authUseCase.login(email, password)
        }
    }

    fun signUp(email: String, password: String, nickname: String, name: String, id: String) {
        viewModelScope.launch {
            _signUpResult.value = authUseCase.signUp(email, password, nickname, name, id)
        }
    }

    fun snsSignUp(nickname: String, name: String) {
        viewModelScope.launch {
            _signUpResult.value = authUseCase.snsSignUp(nickname, name)
        }
    }
}