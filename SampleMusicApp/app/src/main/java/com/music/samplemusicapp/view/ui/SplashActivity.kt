package com.music.samplemusicapp.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.music.samplemusicapp.R
import com.music.samplemusicapp.view.util.SPLASH_DELAY

class SplashActivity : BaseActivity() {

    // SplashActivity properties.
    private var mDelayHandler: Handler? = null

    /**
     * Runnable instance called after 3 seconds.
     */
    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }
}
