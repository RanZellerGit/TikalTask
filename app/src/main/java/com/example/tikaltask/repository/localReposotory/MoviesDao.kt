package com.example.tikaltask.repository.localReposotory

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tikaltask.repository.model.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies WHERE `index` >= :startIndex and `index` < :endIndex")
     fun getMovies(startIndex : Int, endIndex : Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<Movie>)
}