package com.example.movieapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapi.databinding.FragmentFavouriteBinding
import com.example.movieapi.db.MovieDatabase
import com.example.movieapi.model.FavMovieAdapter
import com.example.movieapi.model.MovieList
import com.example.movieapi.viewModel.DetailViewModel
import com.example.movieapi.viewModel.MovieViewModelFactory

class FavouriteFragment : Fragment() {
    lateinit var binding: FragmentFavouriteBinding
    lateinit var viewModel: DetailViewModel
    var mealToSave: MovieList? = null
    lateinit var favListAdapter: FavMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieDb = MovieDatabase.getDbInstance(requireContext())
        val viewmodel = MovieViewModelFactory(movieDb)
        viewModel = ViewModelProvider(this, viewmodel)[DetailViewModel::class.java]
        favListAdapter = FavMovieAdapter()
       // setUp()
        observeFavorites()
        setUpRv()
        setFavClick()
    }

    private fun setFavClick() {
        favListAdapter.setOnItemClickListener {
            if (it.isChecked) {
                Toast.makeText(requireContext(), "favorite movie Added", Toast.LENGTH_SHORT).show()
            } else {
                val position=favListAdapter.click
                viewModel.deleteFavMovie(favListAdapter.differ.currentList[position])
                Toast.makeText(
                    requireContext(),
                    "Removed favourite Movie",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun setUpRv() {
        binding.favrecyclerview.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            adapter = favListAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavDbLLivedata().observe(requireActivity(), Observer { meals ->
            favListAdapter.differ.submitList(meals)
        })
    }

    /*fun setUp() {
        viewModel.getMovieObserve().observe(viewLifecycleOwner, object : Observer<List<MovieList>> {
            override fun onChanged(t: List<MovieList>?) {
                t.let {
                    mealToSave =it
                }
            }

        })
    }*/
}