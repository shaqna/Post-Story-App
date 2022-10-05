package com.ngedev.postcat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ngedev.postcat.external.AppSharedPreference
import com.ngedev.postcat.ui.home.HomeActivity
import com.ngedev.postcat.utils.di.PrefsModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.loadKoinModules


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        loadKoinModules(PrefsModule.appPrefsModule)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            observeUser()
        }, 3000L)
    }

    private fun observeUser() {
        if(isUserAlreadyHere()) {
            startActivity(Intent(this, HomeActivity::class.java)).also {
                finish()
            }
        } else {
            startActivity(Intent(this, MainActivity::class.java)).also {
                finish()
            }
        }
    }


    private fun isUserAlreadyHere(): Boolean {
        val appSharedPreference: AppSharedPreference = get()
        val token = appSharedPreference.fetchAccessToken()
        if (token != null) {
            return true
        }
        return false
    }
}