package com.example.myapplication.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.movieapp.R
import com.example.myapplication.movieapp.databinding.NewPlaylistAlertDialogBinding
import com.example.myapplication.movieapp.databinding.PlaylistModalBottomSheetBinding
import com.example.myapplication.movieapp.domain.model.PlayList
import com.example.myapplication.movieapp.ui.adapter.PlayListAdapter
import com.example.myapplication.movieapp.ui.viewmodel.PlayListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayListBottomSheet(
    private val movieId: Int?,
    private val isFilterMode: Boolean,
    private val onClick: ((String) -> Unit)?,
) :
    BottomSheetDialogFragment() {
    private lateinit var binding: PlaylistModalBottomSheetBinding
    private val viewModel by viewModels<PlayListViewModel>()
    private lateinit var playListAdapter: PlayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = PlaylistModalBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playListAdapter =
            PlayListAdapter(getString(R.string.add_playlist), this::onItemClick, isFilterMode)
        binding.playList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = playListAdapter
        }
        setupDivider()
        fetchLatestPlayLists()
    }

    private fun setupDivider() {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
            ?.let { itemDecorator.setDrawable(it) }
        binding.playList.addItemDecoration(itemDecorator)
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int?, isFilterMode: Boolean = false, onClick: ((String) -> Unit)?): PlayListBottomSheet {
            return PlayListBottomSheet(movieId, isFilterMode, onClick)
        }
    }

    private fun onItemClick(playList: PlayList) {
        dismiss()
        if (isFilterMode) {
            onClick?.invoke(playList.id)
        } else {
            when (playList.id) {
                MovieConstants.NEW_PLAYLIST_ID -> {
                    showPlayListCreationDialog(movieId)
                }
                else -> {
                    movieId?.let {
                        viewModel.updatePlayList(playList, movieId)
                        fetchLatestPlayLists()
                    }
                }
            }
        }
    }

    private fun showPlayListCreationDialog(movieId: Int?) {
        context?.let {
            val inflater = LayoutInflater.from(it)
            val binding = NewPlaylistAlertDialogBinding.inflate(inflater)
            val editText = binding.playlistName
            AlertDialog.Builder(it)
                .setTitle("Enter a playlist name")
                .setView(binding.root)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    movieId?.let {
                        val playList = PlayList(System.currentTimeMillis().toString(),
                            editText.text.toString(),
                            IntArray(movieId))
                        viewModel.createNewPlaylist(playList, movieId)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun fetchLatestPlayLists() {
        viewLifecycleOwner.lifecycleScope.launch {
            playListAdapter.submitList(viewModel.getPlayLists())
        }
    }

}