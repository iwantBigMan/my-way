package com.hyeon.side.sideapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeon.side.sideapp.domain.entity.Friends
import com.hyeon.side.sideapp.domain.usecase.friend.GetFriendsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase
) : ViewModel() {

    private val _friendsList = MutableLiveData<List<Friends>>()
    val friendsList: LiveData<List<Friends>> get() = _friendsList

    fun fetchFriendsList(userId: String) {
        viewModelScope.launch {
            val friends = getFriendsListUseCase(userId)
            _friendsList.value = friends
        }
    }
}

