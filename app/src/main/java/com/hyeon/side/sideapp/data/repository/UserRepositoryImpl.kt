package com.hyeon.side.sideapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.hyeon.side.sideapp.domain.entity.User
import com.hyeon.side.sideapp.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun getUserData(userId: String): List<User> {
        return try {
            val snapshot = firestore.collection("users")
                .get()
                .await()  // 비동기 처리
            snapshot.documents.mapNotNull { it.toObject(User::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
