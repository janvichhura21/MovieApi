package com.example.movieapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.databinding.FragmentWatchlistBinding
import com.example.movieapi.db.MovieDatabase
import com.example.movieapi.model.WatchLaterAdapter
import com.example.movieapi.viewModel.DetailViewModel
import com.example.movieapi.viewModel.MovieViewModelFactory

class WatchListFragment : Fragment() {
    lateinit var watchLaterAdapter: WatchLaterAdapter
    lateinit var viewModel: DetailViewModel

    lateinit var binding: FragmentWatchlistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentWatchlistBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieDb = MovieDatabase.getDbInstance(requireContext())
        val viewmodel = MovieViewModelFactory(movieDb)
        viewModel = ViewModelProvider(this, viewmodel)[DetailViewModel::class.java]
        watchLaterAdapter= WatchLaterAdapter()
        setRv()
        observeFavorites()
        setUpClick()
    }

    private fun setUpClick() {
        watchLaterAdapter.setOnItemClickListener {
            if (!it.isChecked){
                val position=watchLaterAdapter.click
                viewModel.deleteFavMovie(watchLaterAdapter.differ.currentList[position])
                Toast.makeText(
                    requireContext(),
                    "Removed Movie",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setRv() {
        binding.rv.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=watchLaterAdapter
        }
    }
    private fun observeFavorites() {
        viewModel.observeFavDbLLivedata().observe(requireActivity(), Observer { movies ->
            watchLaterAdapter.differ.submitList(movies)
        })
    }

}