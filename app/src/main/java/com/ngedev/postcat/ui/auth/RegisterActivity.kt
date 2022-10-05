package com.ngedev.postcat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.databinding.ActivityRegisterBinding
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.utils.di.AuthModule.authModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(authModule)

        binding.buttonRegister.setOnClickListener {
            with(binding) {
                val name = name.text.toString()
                val email = email.text.toString()
                val password = password.text.toString()

                viewModel.createUser(RegisterRequest(name, email, password))
                    .observe(this@RegisterActivity, ::createUserResponse)
            }
        }
    }

    private fun createUserResponse(resource: Resource<RegisterResponse>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                Log.d("RegisterResponse", resource.data!!.toString())
                startActivity(Intent(this, LoginActivity::class.java)).also {
                    finish()
                }

            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
            }
            is Resource.Loading -> {
                loadingState(true)
            }
            else -> {}
        }
    }

    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
        binding.buttonRegister.isVisible = !state

    }
}