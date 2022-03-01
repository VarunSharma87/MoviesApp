package com.example.myapplication.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.movieapp.BR
import com.example.myapplication.movieapp.databinding.MovieListItemBinding
import com.example.myapplication.movieapp.domain.model.Movie
import com.example.myapplication.movieapp.domain.model.PlayList

class MovieListAdapter(private val onClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffCallback) {

    class MovieViewHolder(private val binding: MovieListItemBinding, val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentMovie: Movie? = null

        init {
            binding.likeButton.setOnClickListener {
                currentMovie?.let {
                    onClick(it)
                }
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie
            binding.setVariable(BR.movie, currentMovie)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClick)
    }

    object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}