package com.arif.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arif.jetpackpro.data.source.local.LocalRepository
import com.arif.jetpackpro.data.source.remote.ApiResponse
import com.arif.jetpackpro.data.source.remote.RemoteRepository
import com.arif.jetpackpro.data.entity.MovieModel
import com.arif.jetpackpro.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private var localRepository: LocalRepository,
    private var remoteRepository: RemoteRepository,
    private var appExecutors: AppExecutors
): MovieDataSource {

    override fun getAllMovies(page: Int): LiveData<Resource<PagedList<MovieModel>>> {
        return object : NetworkBoundResource<PagedList<MovieModel>, List<MovieModel>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieModel>> =
                LivePagedListBuilder(localRepository.getAllMoviesAsPaged(), 20).build()

            override fun shouldFetch(data: PagedList<MovieModel>?): Boolean =
//                data == null || data.isEmpty()
                true

            public override fun createCall(): LiveData<ApiResponse<List<MovieModel>>>? =
                remoteRepository.getAllMoviesAsLiveData(page)

            override fun saveCallResult(data: List<MovieModel>) {
                localRepository.insertMovie(data)
            }
        }.asLiveData()
    }


    override fun getFavoriteMovies(): LiveData<Resource<PagedList<MovieModel>>> {
        return object : NetworkBoundResource<PagedList<MovieModel>, List<MovieModel>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieModel>> =
                LivePagedListBuilder(localRepository.getFavoriteMoviesAsPaged(), 20).build()

            override fun shouldFetch(data: PagedList<MovieModel>?): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<MovieModel>>>? = null

            override fun saveCallResult(data: List<MovieModel>) {}
        }.asLiveData()
    }

    override fun setFavoriteMovie(course: MovieModel, state: Boolean) {
        val runnable = { localRepository.setFavoriteMovie(course, state) }
        appExecutors.diskIO().execute(runnable)
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: MovieRepository? = null
//
//        fun getInstance(
//            localRepository: LocalRepository,
//            remoteData: RemoteRepository,
//            appExecutors: AppExecutors
//        ): MovieRepository {
//            if (INSTANCE == null) {
//                synchronized(MovieRepository::class.java) {
//                    INSTANCE = MovieRepository(localRepository, remoteData, appExecutors)
//                }
//            }
//            return INSTANCE as MovieRepository
//        }
//    }

}