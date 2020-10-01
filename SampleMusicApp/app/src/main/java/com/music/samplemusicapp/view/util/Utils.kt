package com.music.samplemusicapp.view.util

import com.music.samplemusicapp.MusicApplication
import com.music.samplemusicapp.repository.model.MusicEntity

/**
 * Init music model with 10 hard coded data.
 */
fun initMusicModel(pageCount: Int): ArrayList<MusicEntity>? {
    if (pageCount == 0) {
        return null
    }

    for (page in 0 until pageCount) {
        val size = MusicApplication.musicList?.size
        val total = size?.plus(PAGE_INCREASE_COUNT)
        val musicEntity = MusicEntity("Test Song $total", "Test Song Details $total")
        MusicApplication.musicList?.add(musicEntity)
    }

    return MusicApplication.musicList;

}