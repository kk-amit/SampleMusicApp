package com.music.samplemusicapp.view.ui

import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music.samplemusicapp.R
import com.music.samplemusicapp.repository.model.MusicEntity
import com.music.samplemusicapp.repository.model.Response
import com.music.samplemusicapp.view.adapter.MusicListAdapter
import com.music.samplemusicapp.view.util.PAGE_SIZE_COUNT
import com.music.samplemusicapp.view.util.initMusicModel
import com.music.samplemusicapp.viewmodel.MusicAppViewModel
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber

/**
 * Main Activity show the Music List.
 */
class MainActivity : BaseActivity() {

    // Main Activity properties.
    private lateinit var musicAppViewModel: MusicAppViewModel
    private var musicListAdapter: MusicListAdapter? = null
    private val context: Context = this
    private lateinit var viewMusicRecyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var musicList = ArrayList<MusicEntity>()
    private var isMusicCallStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.str_music)
        Timber.d(getString(R.string.str_on_create))
        initView()

        // View Model for Music data.
        musicAppViewModel = ViewModelProvider(this).get(MusicAppViewModel::class.java)

        musicListAdapter = initMusicModel(PAGE_SIZE_COUNT)?.let { MusicListAdapter(context, it) }
        viewMusic.adapter = musicListAdapter


    }

    /**
     * Initialising the activity view.
     */
    private fun initView() {
        // Music List Recycler-view and adapter Setting.
        viewMusicRecyclerView = viewMusic
        linearLayoutManager = LinearLayoutManager(this)
        viewMusic.layoutManager = linearLayoutManager
        viewMusic.setHasFixedSize(true)
        viewMusic.addOnScrollListener(recyclerViewOnScrollListener)
    }

    override fun onResume() {
        super.onResume()
        Timber.d(getString(R.string.str_on_resume))
    }

    /**
     * Returned list of Music records.
     */
    private fun getMusicDataList() {
        isMusicCallStarted = true
        Timber.d(getString(R.string.str_getMusicDataList))
        progressBar.visibility = View.VISIBLE
        musicAppViewModel.fetchMusicList(PAGE_SIZE_COUNT)
            .observe(context as MainActivity, Observer {
                if (it == null || (it as Response<*>).value is Exception) {
                    Timber.d(getString(R.string.str_getMusicDataList_error))
                } else {
                    musicList.clear()
                    musicList.addAll((it as Response<*>).value as ArrayList<MusicEntity>)
                    musicListAdapter?.setMusicListValue(musicList)
                    musicListAdapter?.notifyDataSetChanged()
                }
                progressBar.visibility = View.GONE
                isMusicCallStarted = false
            })

    }

    /**
     * Recyclerview scroll listener.
     */
    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = linearLayoutManager.getChildCount()
                val totalItemCount: Int = linearLayoutManager.getItemCount()
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()
                if (!isFinishing && !isMusicCallStarted) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        progressBar.visibility = View.VISIBLE
                        getMusicDataList()
                    }
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        musicAppViewModel.cancelAllRequests()
    }
}
