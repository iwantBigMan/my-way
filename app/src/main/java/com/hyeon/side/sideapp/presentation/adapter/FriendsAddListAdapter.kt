package com.hyeon.side.sideapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyeon.side.sideapp.R
import com.hyeon.side.sideapp.domain.entity.User


class FriendsAddListAdapter(
    private var userList : List<User>,
    private val onCheckBoxClick: (User) -> Unit) : RecyclerView.Adapter<FriendsAddListAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val friendsNickName: TextView = view.findViewById(R.id.friendNickName)
            private val checkBox: ImageView = view.findViewById(R.id.addFriendCheckBox)


            fun bind(user: User) {
                friendsNickName.text = user.nickname
                checkBox.setOnClickListener {
                    onCheckBoxClick(user)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_add_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }
    
    fun updateFriends(newFriends: List<User>) {
        val diffCallback = FriendsAddDiffCallback(userList, newFriends)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        userList = newFriends
        diffResult.dispatchUpdatesTo(this)
    }
    
    class FriendsAddDiffCallback(
        private val oldList: List<User>,
        private val newList: List<User>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}