package com.hyeon.side.sideapp.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyeon.side.sideapp.databinding.ActivitySnsSignUpBinding
import com.hyeon.side.sideapp.presentation.view.main.MainActivity
import com.hyeon.side.sideapp.presentation.viewmodel.UserDataViewModel

class SnsSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySnsSignUpBinding
    private val viewModel: UserDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnsSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.signUpResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "SNS 회원가입 성공", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "SNS 회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            val nickname = binding.nickname.text.toString()
            val name = binding.username.text.toString()
            viewModel.snsSignUp(nickname, name)
        }
    }
}