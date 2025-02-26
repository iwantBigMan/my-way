package com.hyeon.side.sideapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.side.sideapp.R
import com.hyeon.side.sideapp.domain.entity.Friends

class FriendsListAdapter(
    private var friendList: List<Friends>, // 친구 목록 데이터
    private val onChatClick: (Friends) -> Unit // 채팅 버튼 클릭 이벤트
) : RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>() {

    inner class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val profileImage: ImageView = view.findViewById(R.id.profileImage)
        private val friendName: TextView = view.findViewById(R.id.friendName)
        private val statusMessage: TextView = view.findViewById(R.id.statusMessage)
        private val chatButton: ImageView = view.findViewById(R.id.chatButton)

        fun bind(friend: Friends) {
            // Load profile image using an image loading library like Glide or Picasso
            // Glide.with(profileImage.context).load(friend.profileImageUrl).into(profileImage)
            profileImage.setImageResource(R.drawable.ic_profile_placeholder)
            friendName.text = friend.nickname
            statusMessage.text = friend.statusMessage
            chatButton.setOnClickListener { onChatClick(friend)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_item, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(friendList[position])
    }

    override fun getItemCount(): Int = friendList.size

    fun updateFriends(newFriends: List<Friends>) {
        val diffCallback = FriendsDiffCallback(friendList, newFriends)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        friendList = newFriends
        diffResult.dispatchUpdatesTo(this)
    }

    class FriendsDiffCallback(
        private val oldList: List<Friends>,
        private val newList: List<Friends>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].nickname == newList[newItemPosition].nickname
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem && oldItem.statusMessage == newItem.statusMessage
        }
    }
}