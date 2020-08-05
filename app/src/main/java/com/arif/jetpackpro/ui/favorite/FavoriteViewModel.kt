package com.arif.jetpackpro.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arif.jetpackpro.data.repository.MovieRepository
import com.arif.jetpackpro.data.entity.MovieModel
import com.arif.jetpackpro.data.repository.Resource
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private var movieRepository: MovieRepository) : ViewModel() {

    fun getDataMovie(): LiveData<Resource<PagedList<MovieModel>>> = movieRepository.getFavoriteMovies()

}

