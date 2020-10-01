package com.music.samplemusicapp

import android.app.Application
import android.content.Context
import com.music.samplemusicapp.repository.model.MusicEntity
import timber.log.Timber

class MusicApplication : Application() {

    /**
     * Instance for MusicApplication
     */
    companion object {
        lateinit var mInstance: MusicApplication
        var mContext: Context? = null
        var musicList: ArrayList<MusicEntity>? = null
    }


    override fun onCreate() {
        super.onCreate()
        Timber.d(getString(R.string.str_on_create))
        mInstance = this
        mContext = applicationContext
        musicList = ArrayList()
        // setup timber debug tree for debug build
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}