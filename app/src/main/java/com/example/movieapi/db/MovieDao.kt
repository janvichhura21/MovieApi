package com.example.movieapi.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapi.model.Movie
import com.example.movieapi.model.MovieList

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieList)

    @Delete
    suspend fun deleteMovie(movie: MovieList)

    @Query("SELECT * FROM movieTableInfo")
    fun getMovieLiveData(): LiveData<List<MovieList>>

}