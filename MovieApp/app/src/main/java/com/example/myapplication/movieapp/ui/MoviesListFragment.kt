package com.example.myapplication.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.movieapp.R
import com.example.myapplication.movieapp.databinding.FragmentFirstBinding
import com.example.myapplication.movieapp.domain.model.Movie
import com.example.myapplication.movieapp.ui.adapter.MovieListAdapter
import com.example.myapplication.movieapp.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private val viewModel by activityViewModels<MoviesViewModel>()
    private var _binding: FragmentFirstBinding? = null
    private lateinit var moviesAdapter: MovieListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filterKey().observe(viewLifecycleOwner) {
            filterMovies(it)
        }
        moviesAdapter = MovieListAdapter(this::onItemClick)
        binding.movieList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
        setupDivider()
    }

    override fun onResume() {
        super.onResume()
        fetchMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupDivider() {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable)
            ?.let { itemDecorator.setDrawable(it) }
        binding.movieList.addItemDecoration(itemDecorator)
    }

    private fun onItemClick(movie: Movie) {
        PlayListBottomSheet.newInstance(movie.id, false, null)
            .show(parentFragmentManager.beginTransaction(), null)
    }

    private fun filterMovies(playListId: String?) {
        if (playListId.isNullOrEmpty()) {
            fetchMovies()
        } else {
            viewLifecycleOwner.lifecycleScope.launch {
                val result = viewModel.getMoviesInPlayList(playListId)
                result?.let {
                    moviesAdapter.submitData(PagingData.from(it))
                }
            }
        }
    }

    private fun fetchMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }
}