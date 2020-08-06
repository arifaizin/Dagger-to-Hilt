package com.arif.daggerhilt.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.arif.daggerhilt.data.entity.MovieModel
import com.arif.daggerhilt.data.repository.MovieRepository

class DetailMovieViewModel @ViewModelInject constructor(private var movieRepository: MovieRepository) : ViewModel() {
    fun setFavoriteMovie(dataMovie: MovieModel, newStatus:Boolean) =
        movieRepository.setFavoriteMovie(dataMovie, newStatus)
}

