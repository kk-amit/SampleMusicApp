package com.music.samplemusicapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.music.samplemusicapp.repository.model.MusicEntity
import com.music.samplemusicapp.repository.model.Response
import com.music.samplemusicapp.view.util.SLEEP_TIME
import com.music.samplemusicapp.view.util.initMusicModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Music app viewmodel, notify the view once task completed.
 */
class MusicAppViewModel(application: Application) : AndroidViewModel(application) {

    // Kotlin coroutine properties.
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    /**
     * Live data reference for fetch DB operation.
     */
    private lateinit var fetchMusicData: MutableLiveData<Any>

    /**
     * Fetch the Music list.
     *
     * @return LiveData is a data holder class that can be observed within a given lifecycle.
     */
    fun fetchMusicList(itemCount: Int?): LiveData<Any> {
        fetchMusicData = MutableLiveData()
        val cityResponse: Response<Any>? = Response()
        scope.launch {
            val musicData: List<MusicEntity>? = itemCount?.let { initMusicModel(it) }
            when {
                musicData == null -> {
                    cityResponse?.value = Exception()
                }
                musicData.isEmpty() -> {
                    cityResponse?.value = Exception()
                }
                else -> {
                    cityResponse?.value = musicData
                }
            }
            // block main thread for 1 seconds to keep JVM alive
            Thread.sleep(SLEEP_TIME)
            fetchMusicData.postValue(cityResponse)
        }
        return fetchMusicData
    }

    /**
     * Cancel the  coroutine request.
     */
    fun cancelAllRequests() = coroutineContext.cancel()
}