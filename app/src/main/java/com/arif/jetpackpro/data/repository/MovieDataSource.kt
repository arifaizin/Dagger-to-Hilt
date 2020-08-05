package com.arif.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arif.jetpackpro.data.entity.MovieModel

interface MovieDataSource {
    fun getAllMovies(page: Int): LiveData<Resource<PagedList<MovieModel>>>

    fun getFavoriteMovies(): LiveData<Resource<PagedList<MovieModel>>>

    fun setFavoriteMovie(course: MovieModel, state: Boolean)
}