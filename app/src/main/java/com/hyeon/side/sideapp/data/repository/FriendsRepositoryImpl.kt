package com.hyeon.side.sideapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.hyeon.side.sideapp.domain.entity.Friends
import com.hyeon.side.sideapp.domain.repository.FriendsRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FriendsRepository {
    override suspend fun getFriendsList(userId: String): List<Friends> {
        return try {
            val snapshot = firestore.collection("users").document(userId).collection("friends").get().await()
            snapshot.documents.map { document ->
                Friends(
                    id = document.getString("id") ?: "",
                    nickname = document.getString("nickname") ?: "",
                    email = document.getString("email") ?: "",
                    statusMessage = document.getString("statusMessage") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
