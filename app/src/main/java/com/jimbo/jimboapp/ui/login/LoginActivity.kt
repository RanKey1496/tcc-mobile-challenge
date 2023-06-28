package com.jimbo.jimboapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jimbo.jimboapp.R
import com.jimbo.jimboapp.databinding.ActivityLoginBinding
import com.jimbo.jimboapp.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameTextInput.doOnTextChanged { text, _, _, _ ->
            loginViewModel.updateUsername(text.toString())
        }

        binding.passworTextInput.doOnTextChanged { text, _, _, _ ->
            loginViewModel.updatePassword(text.toString())
        }

        binding.loginButton.setOnClickListener {
            loginViewModel.login()
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect {
                    withContext(Dispatchers.Main) {
                        binding.loginButton.isVisible = !it.loading
                        binding.loginLoadingIndicator.isVisible = it.loading
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.authRequestState.collect {
                    when (it) {
                        is AuthRequestStatus.Success -> {
                            val intent = Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                            intent.putExtra("TOKEN", it.token)

                            startActivity(intent)
                            this@LoginActivity.finish()
                        }
                        else -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Error Authenticating",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}