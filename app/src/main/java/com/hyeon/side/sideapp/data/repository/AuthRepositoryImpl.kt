package com.hyeon.side.sideapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyeon.side.sideapp.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun login(id: String, password: String): Boolean {
        return try {
            val userDocument = firestore.collection("users").document(id).get().await()
            val storedPassword = userDocument.getString("password")
            storedPassword == password
        } catch (e: Exception) {
            false
        }
    }


    override suspend fun signUp(email: String, password: String, nickname: String, name: String, id: String): Boolean {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = firebaseAuth.currentUser
            user?.let {
                firestore.collection("users").document(it.uid).set(mapOf("nickname" to nickname))
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun snsSignUp(nickname: String, name: String): Boolean {
        val user = firebaseAuth.currentUser
        return if (user != null) {
            try {
                firestore.collection("users").document(user.uid).set(mapOf("nickname" to nickname, "name" to name))
                true
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }
}