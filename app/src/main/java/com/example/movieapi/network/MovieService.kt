package com.example.movieapi.network

import com.example.movieapi.model.Movie
import com.example.movieapi.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL="https://api.themoviedb.org/3/"
const val API_KEY="158d246200c8fe8baf6e9a2c357b7433"
interface MovieService {

    @GET("movie/popular?api_key=${API_KEY}")
    fun getMovie(): Call<Movie>

    @GET("movie/popular?api_key=${API_KEY}")
    fun getFav(@Query("id")id:Int):Call<Movie>
}