package com.example.myapplication.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.movieapp.BR
import com.example.myapplication.movieapp.databinding.PlayListItemBinding
import com.example.myapplication.movieapp.domain.model.PlayList
import com.example.myapplication.movieapp.ui.MovieConstants

class PlayListAdapter(
    private val newPlayListString: String,
    private val onClick: (PlayList) -> Unit,
    private val isFilterMode: Boolean
) :
    ListAdapter<PlayList, PlayListAdapter.PlayListViewHolder>(PlayListDiffCallback) {

    class PlayListViewHolder(
        private val binding: PlayListItemBinding,
        val onClick: (PlayList) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentPlayList: PlayList? = null

        init {
            binding.playListItem.setOnClickListener {
                currentPlayList?.let {
                    onClick(it)
                }
            }
        }

        fun bind(playList: PlayList) {
            currentPlayList = playList
            binding.setVariable(BR.playList, currentPlayList)
        }
    }

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        return if (isFilterMode)
            count
        else
            count + 1
    }

    override fun getItem(position: Int): PlayList {
        if (position == itemCount - 1 && isFilterMode.not()) {
            return PlayList(MovieConstants.NEW_PLAYLIST_ID, newPlayListString, null)
        }
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val binding =
            PlayListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    object PlayListDiffCallback : DiffUtil.ItemCallback<PlayList>() {
        override fun areItemsTheSame(oldItem: PlayList, newItem: PlayList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlayList, newItem: PlayList): Boolean {
            return oldItem.id == newItem.id
        }
    }
}