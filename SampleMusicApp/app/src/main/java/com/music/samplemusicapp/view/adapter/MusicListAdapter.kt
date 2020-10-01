package com.music.samplemusicapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.music.samplemusicapp.R
import com.music.samplemusicapp.repository.model.MusicEntity
import kotlinx.android.synthetic.main.music_list_item.view.*

/**
 * Custom Music List Adapter.
 */
class MusicListAdapter(
    private var context: Context, private var musicList: ArrayList<MusicEntity>
) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {

    // MusicListAdapter properties
    private var musicListModel = ArrayList<MusicEntity>(musicList)

    // Set music data list.
    fun setMusicListValue(updatedMusicList: ArrayList<MusicEntity>) {
        musicListModel.clear()
        this.musicListModel.addAll(updatedMusicList)
    }


    class MusicListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent = itemView.cvRowParent!!
        val ivPic = itemView.ivIcon!!
        val songName = itemView.tvName!!
        val songDetails = itemView.tvMessage!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListViewHolder {
        return MusicListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.music_list_row_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return musicListModel.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MusicListViewHolder, position: Int) {
        val songName = musicListModel[position].song
        val songDetails = musicListModel[position].songDetails
        holder.ivPic.setBackgroundResource(R.drawable.ic_music)
        holder.songName.text = songName
        holder.songDetails.text = songDetails
    }
}
