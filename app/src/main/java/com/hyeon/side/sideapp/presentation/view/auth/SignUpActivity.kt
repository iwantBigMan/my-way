package com.hyeon.side.sideapp.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hyeon.side.sideapp.databinding.ActivitySignUpBinding
import com.hyeon.side.sideapp.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: UserDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.signUpResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        var spDomain = ""

        binding.spinnerEmailDomain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val domain = parent?.getItemAtPosition(position).toString()
                val userDomain = binding.userEmailDomain.text.toString()
                if (domain == "직접입력") {
                    binding.spinnerEmailDomain.visibility = View.GONE
                    binding.userEmailDomain.visibility = View.VISIBLE
                    spDomain = userDomain
                } else {
                    binding.userEmailDomain.visibility = View.GONE
                    binding.spinnerEmailDomain.visibility = View.VISIBLE
                    spDomain = domain
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.email.text.toString() + "@" + spDomain
            val password = binding.password.text.toString()
            val nickname = binding.nickname.text.toString()
            val name = binding.username.text.toString()
            val id = binding.userId.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "잘못된 이메일 형식입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.signUp(email, password, nickname, name, id)
        }
    }
}