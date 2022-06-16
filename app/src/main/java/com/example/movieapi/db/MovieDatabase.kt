package com.example.movieapi.db

import android.content.Context
import androidx.room.*
import com.example.movieapi.model.MovieList

@Database(entities = [MovieList::class], version = 1)
@TypeConverters(IntListConvertors::class)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao():MovieDao
    companion object{
        @Volatile
        private var INSTANCE:MovieDatabase?=null

        @Synchronized
        fun getDbInstance(context: Context):MovieDatabase{
            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context,MovieDatabase::class.java,"mealname")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MovieDatabase
        }
    }
}