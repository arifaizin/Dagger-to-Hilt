package com.arif.daggerhilt.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arif.daggerhilt.data.entity.MovieModel
import com.arif.daggerhilt.data.repository.MovieRepository
import com.arif.daggerhilt.data.repository.Resource

class MovieViewModel @ViewModelInject constructor(private var movieRepository: MovieRepository) : ViewModel() {

    fun getDataMovie(page: Int): LiveData<Resource<PagedList<MovieModel>>> = movieRepository.getAllMovies(page)

}

