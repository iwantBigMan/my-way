package com.hyeon.side.sideapp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyeon.side.sideapp.presentation.view.chat.ChatListFragment
import com.hyeon.side.sideapp.presentation.view.friends.FriendsFragment
import com.hyeon.side.sideapp.presentation.view.setting.MenuFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FriendsFragment()
            1 -> ChatListFragment()
            2 -> MenuFragment()
            else -> FriendsFragment()
        }
    }
}
