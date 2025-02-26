package com.hyeon.side.sideapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hyeon.side.sideapp.domain.entity.ChatData
import kotlinx.coroutines.launch

class ChatViewModel(private val userId: String, private val friendId: String) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val chatCollection = db.collection("users").document(userId).collection("chats")

    private val _chatList = MutableLiveData<List<ChatData>>()
    val chatList: LiveData<List<ChatData>> = _chatList

    init {
        fetchChats()
    }

    private fun fetchChats() {
        chatCollection.orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            val chatItems = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(ChatData::class.java)
            } ?: emptyList()

            _chatList.value = chatItems
        }
    }

    fun sendMessage(name: String, message: String) {
        val chatMessage = ChatData(name, message, System.currentTimeMillis().toString())

        viewModelScope.launch {
            chatCollection.add(chatMessage)
        }
    }
}

class ChatViewModelFactory(private val userId: String, private val friendId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(userId, friendId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}