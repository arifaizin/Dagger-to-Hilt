package com.arif.jetpackpro.ui.detail

import androidx.lifecycle.ViewModel
import com.arif.jetpackpro.data.repository.MovieRepository
import com.arif.jetpackpro.data.entity.MovieModel
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(private var movieRepository: MovieRepository) : ViewModel() {
    fun setFavoriteMovie(dataMovie: MovieModel, newStatus:Boolean) =
        movieRepository.setFavoriteMovie(dataMovie, newStatus)
}

