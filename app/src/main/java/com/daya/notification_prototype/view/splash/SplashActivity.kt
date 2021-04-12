package com.daya.notification_prototype.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.daya.notification_prototype.R
import com.daya.notification_prototype.view.main.MainActivity
import com.daya.notification_prototype.view.ui.login.LoginActivity
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val isUserLogin = false
        lifecycleScope.launch {
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