package com.ngedev.postcat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.databinding.ActivityLoginBinding
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.external.AppSharedPreference
import com.ngedev.postcat.ui.home.HomeActivity
import com.ngedev.postcat.utils.di.AuthModule
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: AuthViewModel by viewModel()
    private val prefs: AppSharedPreference by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(AuthModule.authModule)

        with(binding) {
            buttonLogin.setOnClickListener {
                val email = email.text.toString().trim()
                val password = password.text.toString()

                viewModel.login(LoginRequest(email, password))
                    .observe(this@LoginActivity, ::loginResponse)
            }

        }

    }

    private fun loginResponse(resource: Resource<LoginResponse>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                resource.data?.let { response ->
                    prefs.saveAccessToken(response.loginResult.token)
                }
                startActivity(Intent(this, HomeActivity::class.java)).also {
                    finishAffinity()
                }
            }
            is Resource.Error -> {
                loadingState(false)
                Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                Log.d("MyERROR", resource.message.toString())
            }
            is Resource.Loading -> {
                loadingState(true)
            }
            else -> {}
        }
    }

    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
        binding.buttonLogin.isVisible = !state
    }
}