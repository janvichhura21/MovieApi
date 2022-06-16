package com.example.movieapi.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapi.WatchListFragment
import com.example.movieapi.databinding.ActivityDetailBinding
import com.example.movieapi.db.MovieDatabase
import com.example.movieapi.model.MovieList
import com.example.movieapi.viewModel.DetailViewModel
import com.example.movieapi.viewModel.MovieViewModelFactory

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    var mealToSave:MovieList?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieDb=MovieDatabase.getDbInstance(this)
        val viewmodel=MovieViewModelFactory(movieDb)
        viewModel= ViewModelProvider(this,viewmodel)[DetailViewModel::class.java]
        viewModel.getMovie()
        val id=intent.getIntExtra("names",1)
            val name = intent.getStringExtra("names")
            val image = intent.getStringExtra("images")
            val overview = intent.getStringExtra("Overview")
            val releaseDate = intent.getStringExtra("ReleaseDate")
            val genre = intent.getStringExtra("Genre")
            val ratings = intent.getIntExtra("ratings", 1)


            binding.overView.setText(overview)
            binding.release.setText(releaseDate)
            binding.Genre.setText(genre)
            binding.collapsingtoolbar.title = name
            binding.name.setText(name)
            Log.d("name", "${name}")
            Glide.with(this)
                .load(image)
                .into(binding.movieImage)
            binding.progressBar.progress = ratings/100
            binding.progressTxt.text="${ratings/100}%"


        viewModel.getFav(id)
        setUpMv()
        addFav()
        addSave()


    }

    private fun addSave() {
        binding.wishbtn.setOnClickListener {
            Toast.makeText(this, "Add to WatchLater", Toast.LENGTH_SHORT).show()
            viewModel.insertFavMovie(mealToSave!!)

        }
    }

    private fun addFav() {
        binding.favbtn.setOnClickListener {
            Toast.makeText(this, "Add to favourite", Toast.LENGTH_SHORT).show()
            viewModel.insertFavMovie(mealToSave!! )
        }
    }

    fun setUpMv(){
        viewModel.ObserveData().observe(this,object :Observer<MovieList>{
            override fun onChanged(t: MovieList?) {
                onResponse()
                mealToSave=t
            }

        })
    }

    fun onResponse(){
        binding.progressbar.visibility= View.INVISIBLE
    }
}