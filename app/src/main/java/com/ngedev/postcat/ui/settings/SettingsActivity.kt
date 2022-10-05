package com.ngedev.postcat.ui.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ngedev.postcat.MainActivity
import com.ngedev.postcat.R
import com.ngedev.postcat.databinding.ActivitySettingsBinding
import com.ngedev.postcat.external.AppSharedPreference
import org.koin.android.ext.android.inject

class SettingsActivity : AppCompatActivity() {

    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    private val prefs: AppSharedPreference by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            localizationLayout.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            logoutLayout.setOnClickListener {
                showLogoutDialog()
            }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.logout_dialog_title))
            .setMessage(getString(R.string.logout_dialog_message))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.logout)) { _, _ ->
                deleteAccessToken()
                Intent(this, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finishAffinity()
                }
                Toast.makeText(
                    this,
                    getString(R.string.logout_message_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            .show()
    }

    private fun deleteAccessToken() {
        prefs.deleteAccessToken()
    }
}