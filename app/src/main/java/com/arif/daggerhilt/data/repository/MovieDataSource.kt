package com.arif.daggerhilt.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arif.daggerhilt.data.entity.MovieModel

interface MovieDataSource {
    fun getAllMovies(page: Int): LiveData<Resource<PagedList<MovieModel>>>

    fun getFavoriteMovies(): LiveData<Resource<PagedList<MovieModel>>>

    fun setFavoriteMovie(course: MovieModel, state: Boolean)
}