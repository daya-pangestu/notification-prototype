package com.daya.notification_prototype.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import androidx.lifecycle.lifecycleScope
import com.daya.notification_prototype.R
import com.daya.notification_prototype.view.login.LoginActivity
import com.daya.notification_prototype.view.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            // set an exit transition
            exitTransition = Explode()
        }

        setContentView(R.layout.activity_splash)

        val isUserLogin = false

        lifecycleScope.launch {
            delay(2000)
            val intent =if (isUserLogin) {
                 Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()

        }

    }
}