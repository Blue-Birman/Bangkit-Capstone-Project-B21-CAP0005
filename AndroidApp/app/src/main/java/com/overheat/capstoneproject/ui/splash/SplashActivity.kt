package com.overheat.capstoneproject.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.overheat.capstoneproject.MainActivity
import com.overheat.capstoneproject.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            val moveToMain = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(moveToMain)
            finish()
        }, 2000)
    }
}