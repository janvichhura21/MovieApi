package com.example.movieapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.databinding.FragmentHomeBinding
import com.example.movieapi.model.MovieAdapter
import com.example.movieapi.viewModel.MovieViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
   private val viewModel:MovieViewModel by viewModels()
    lateinit var movieAdapter: MovieAdapter
    companion object{
        val movieId="com.example.movieapi.movieId"
        val movieName="com.example.movieapi.movieName"
        val movieThumb="com.example.movieapi.movieThumb"
        val movieOverView="com.example.movieapi.movieOverView"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter= MovieAdapter(requireContext())
        viewModel.getMovie()
        setUpRv()
    }


    private fun setUpRv() {
        binding.rv.apply {
            viewModel.getMovieObserve().observe(viewLifecycleOwner, Observer {
                Log.d("janvi",it.toString())
                movieAdapter.getMovieItems(it)
            })
            layoutManager=LinearLayoutManager(requireActivity())
            adapter=movieAdapter
        }
    }
}