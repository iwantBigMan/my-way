package com.hyeon.side.sideapp.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyeon.side.sideapp.databinding.ActivityLoginBinding
import com.hyeon.side.sideapp.presentation.view.main.MainActivity
import com.hyeon.side.sideapp.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.signInResult.observe(this) { success ->
            if (success) {
                navigateToMainActivity()
            } else {
                showToast("로그인 실패")
            }
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val id = binding.userId.text.toString()
            val password = binding.password.text.toString()
            viewModel.signIn(id, password)
        }
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("nickname", viewModel.userData.value?.nickname)
            putExtra("userId", viewModel.userData.value?.id)
        }
        startActivity(intent)
    }

    private fun navigateToSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}