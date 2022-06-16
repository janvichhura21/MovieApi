package com.example.movieapi.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapi.db.MovieDatabase
import com.example.movieapi.model.Movie
import com.example.movieapi.model.MovieList
import com.example.movieapi.network.RetrofitHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel:ViewModel() {

    val apiList:MutableLiveData<List<MovieList>> = MutableLiveData()
    fun getMovie()=viewModelScope.launch {
        RetrofitHelper.getInstance.getMovie().enqueue(object :Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    apiList.value=response.body()!!.results
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("task","error",t)
            }

        })
    }
    fun getMovieObserve(): LiveData<List<MovieList>> {
        return apiList
    }


}