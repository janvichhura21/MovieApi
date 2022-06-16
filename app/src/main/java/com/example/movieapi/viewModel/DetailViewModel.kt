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

class DetailViewModel(val movieDatabase: MovieDatabase):ViewModel() {

    val movieDb=movieDatabase.movieDao().getMovieLiveData()
    val apiLiveData: MutableLiveData<MovieList> = MutableLiveData()
    val apiData:MutableLiveData<MovieList> = MutableLiveData()
    fun getFav(id:Int)=viewModelScope.launch {
        RetrofitHelper.getInstance.getFav(id).enqueue(object :Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                apiData.value=response.body()!!.results[0]
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
    fun getMovie()=viewModelScope.launch {
        RetrofitHelper.getInstance.getMovie().enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val result=response.body()
                    apiLiveData.value=result!!.results[1]
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("task","error",t)
            }

        })
    }

    fun ObserveData():LiveData<MovieList>{
        return apiData
    }
    fun getMovieObserve(): LiveData<MovieList> {
        return apiLiveData
    }
    fun insertFavMovie(movie: MovieList)=viewModelScope.launch{
        movieDatabase.movieDao().insertMovie(movie)
    }

    fun deleteFavMovie(movie: MovieList) =viewModelScope.launch {
        movieDatabase.movieDao().deleteMovie(movie)
    }
    fun observeFavDbLLivedata(): LiveData<List<MovieList>> {
        return movieDb
    }
}