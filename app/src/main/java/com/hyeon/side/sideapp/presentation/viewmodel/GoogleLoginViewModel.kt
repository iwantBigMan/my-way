package com.hyeon.side.sideapp.presentation.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hyeon.side.sideapp.R
import com.hyeon.side.sideapp.presentation.view.auth.SnsSignUpActivity
import com.hyeon.side.sideapp.presentation.view.main.MainActivity
import kotlinx.coroutines.launch

class GoogleLoginViewModel(application: Application) : AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val googleSignInClient: GoogleSignInClient
    private val _googleSignInResult = MutableLiveData<Boolean>()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    fun setSignInLauncher(activity: AppCompatActivity) {
        signInLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Log.e("GoogleLogin", "Google 로그인에 실패했습니다: ${e.statusCode}")
                Toast.makeText(activity, "Google 로그인에 실패했습니다: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun googleSignIn() {
        // Google 로그인 로직
        // 성공 시:
        val user = auth.currentUser
        if (user != null) {
            val userDoc = firestore.collection("users").document(user.uid)
            userDoc.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val nickname = document.getString("nickname")
                    val name = document.getString("name")
                    if (nickname.isNullOrEmpty() || name.isNullOrEmpty()) {
                        // SNS 회원가입 페이지로 리디렉션
                        val intent = Intent(getApplication(), SnsSignUpActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("email", user.email)
                        getApplication<Application>().startActivity(intent)
                    } else {
                        // 메인 액티비티로 이동
                        val intent = Intent(getApplication(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("nickname", nickname)
                        getApplication<Application>().startActivity(intent)
                    }
                } else {
                    // SNS 회원가입 페이지로 리디렉션
                    val intent = Intent(getApplication(), SnsSignUpActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("email", user.email)
                    getApplication<Application>().startActivity(intent)
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModelScope.launch {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userDoc = firestore.collection("users").document(user!!.uid)
                        userDoc.set(mapOf("email" to user.email))

                    } else {
                        Toast.makeText(getApplication(), "Firebase 인증에 실패했습니다: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}