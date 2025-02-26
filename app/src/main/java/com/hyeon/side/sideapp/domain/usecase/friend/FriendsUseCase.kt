package com.hyeon.side.sideapp.domain.usecase.friend

import com.hyeon.side.sideapp.domain.entity.Friends
import com.hyeon.side.sideapp.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFriendsListUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(userId: String): List<Friends> {
        return repository.getFriendsList(userId)
    }
}
