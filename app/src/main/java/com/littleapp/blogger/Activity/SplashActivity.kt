package com.littleapp.blogger.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.littleapp.blogger.unit.CLASS
import com.littleapp.blogger.unit.THEME
import com.littleapp.blogger.unit.VOID
import com.littleapp.blogger.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private val context: Context = this@SplashActivity

    private val timePerSecond = 2
    private val timeFinal = TIME_PER_MILLIS * timePerSecond

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({ launch() }, timeFinal.toLong())
    }

    private fun launch() {
        VOID.startActivity(context, CLASS.MAIN)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TIME_PER_MILLIS = 1000
    }
}