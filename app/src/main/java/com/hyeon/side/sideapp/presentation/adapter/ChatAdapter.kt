package com.hyeon.side.sideapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.side.sideapp.R
import com.hyeon.side.sideapp.domain.entity.ChatData


class ChatAdapter(private var chatList: List<ChatData>, private val currentUser: String) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val receivedMessageLayout: View = view.findViewById(R.id.receivedMessageLayout)
        val receivedMessage: TextView = view.findViewById(R.id.receivedMessage)
        val sentMessageLayout: View = view.findViewById(R.id.sentMessageLayout)
        val sentMessage: TextView = view.findViewById(R.id.sentMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]

        if (chat.name == currentUser) {
            // 내가 보낸 메시지
            holder.sentMessageLayout.visibility = View.VISIBLE
            holder.receivedMessageLayout.visibility = View.GONE
            holder.sentMessage.text = chat.message
        } else {
            // 상대방이 보낸 메시지
            holder.sentMessageLayout.visibility = View.GONE
            holder.receivedMessageLayout.visibility = View.VISIBLE
            holder.receivedMessage.text = chat.message
        }
    }

    override fun getItemCount(): Int = chatList.size

    fun updateList(newList: List<ChatData>) {
        chatList = newList
        notifyDataSetChanged()
    }
}
