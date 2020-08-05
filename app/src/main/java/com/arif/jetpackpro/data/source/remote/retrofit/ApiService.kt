package com.arif.jetpackpro.data.source.remote.retrofit

import com.arif.jetpackpro.BuildConfig
import com.arif.jetpackpro.data.entity.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing?api_key=${BuildConfig.API_KEY}")
    fun getMovieAsync(
        @Query("page") page: Int
    ): Deferred<MovieResponse>
}