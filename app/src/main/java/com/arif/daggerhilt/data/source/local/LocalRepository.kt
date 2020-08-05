package com.arif.daggerhilt.data.source.local

import androidx.paging.DataSource
import com.arif.daggerhilt.data.entity.MovieModel
import com.arif.daggerhilt.data.source.local.room.MovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(private val mMovieDao: MovieDao) {

    fun getAllMoviesAsPaged(): DataSource.Factory<Int, MovieModel> = mMovieDao.getMovies()

    fun getFavoriteMoviesAsPaged(): DataSource.Factory<Int, MovieModel> = mMovieDao.getFavoriteMovie()

    fun insertMovie(movie: List<MovieModel>) {
        mMovieDao.insertMovies(movie)
    }

    fun setFavoriteMovie(movie: MovieModel, state: Boolean) {
        movie.isFavorite = state
        mMovieDao.updateMovie(movie)
    }

//    companion object {
//        private var INSTANCE: LocalRepository? = null
//
//        fun getInstance(academyDao: MovieDao): LocalRepository {
//            if (INSTANCE == null) {
//                INSTANCE = LocalRepository(academyDao)
//            }
//            return INSTANCE as LocalRepository
//        }
//    }
}