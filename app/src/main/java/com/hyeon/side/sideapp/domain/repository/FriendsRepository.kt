package com.hyeon.side.sideapp.domain.repository

import com.hyeon.side.sideapp.domain.entity.Friends

interface FriendsRepository {
    suspend fun getFriendsList(userId: String): List<Friends>
}
