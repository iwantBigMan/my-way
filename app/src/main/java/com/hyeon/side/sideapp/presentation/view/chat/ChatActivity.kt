package com.hyeon.side.sideapp.presentation.view.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyeon.side.sideapp.presentation.adapter.ChatAdapter
import com.hyeon.side.sideapp.databinding.ActivityChatBinding
import com.hyeon.side.sideapp.presentation.viewmodel.ChatViewModel
import com.hyeon.side.sideapp.presentation.viewmodel.ChatViewModelFactory

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatAdapter: ChatAdapter

    private val friendId: String by lazy { intent.getStringExtra("FRIEND_ID") ?: "" }
    private val friendNickname: String by lazy { intent.getStringExtra("FRIEND_NICKNAME") ?: "" }
    private val userId: String by lazy { intent.getStringExtra("userId") ?: "" }
    private val userNickname: String by lazy { intent.getStringExtra("nickname") ?: "" }
    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            userId, friendId

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatAdapter = ChatAdapter(emptyList(), userNickname)
        binding.chatList.layoutManager = LinearLayoutManager(this)
        binding.chatList.adapter = chatAdapter

        chatViewModel.chatList.observe(this) { chatList ->
            chatAdapter.updateList(chatList)
            binding.chatList.scrollToPosition(chatList.size - 1) // Scroll to the latest message
        }

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString()
            if (message.isNotEmpty()) {
                chatViewModel.sendMessage(userNickname, message)
                binding.messageInput.text.clear()
            }
        }
    }
}