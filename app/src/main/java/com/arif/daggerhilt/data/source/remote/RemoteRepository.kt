package com.arif.daggerhilt.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arif.daggerhilt.data.entity.MovieModel
import com.arif.daggerhilt.data.source.remote.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val service: ApiService) {
//    companion object {
//        @Volatile
//        private var instance: RemoteRepository? = null
//
//        fun getInstance(service: ApiService): RemoteRepository =
//            instance ?: synchronized(this) {
//                instance ?: RemoteRepository(service)
//            }
//    }

    fun getAllMoviesAsLiveData(page: Int): LiveData<ApiResponse<List<MovieModel>>>{
        val resultMovie = MutableLiveData<ApiResponse<List<MovieModel>>>()
        GlobalScope.launch {
            try {
                val postsRequest = service.getMovieAsync(page)
                val postsResponse = postsRequest.await().results
                resultMovie.postValue(ApiResponse.success(postsResponse as List<MovieModel>))
            } catch (e: Exception) {
            }
        }
        return resultMovie
    }

}