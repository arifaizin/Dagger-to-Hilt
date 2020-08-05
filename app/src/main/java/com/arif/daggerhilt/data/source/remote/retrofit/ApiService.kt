package com.arif.daggerhilt.data.source.remote.retrofit

import com.arif.daggerhilt.BuildConfig
import com.arif.daggerhilt.data.entity.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing?api_key=${BuildConfig.API_KEY}")
    fun getMovieAsync(
        @Query("page") page: Int
    ): Deferred<MovieResponse>
}