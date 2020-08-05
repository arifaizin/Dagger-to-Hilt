package com.arif.daggerhilt.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.arif.daggerhilt.data.entity.MovieModel

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): DataSource.Factory<Int, MovieModel>

    @Query("SELECT * FROM movie where isFavorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: List<MovieModel>): LongArray

    @Update
    fun updateMovie(movie: MovieModel): Int
}