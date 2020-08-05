package com.arif.jetpackpro.di

// Before Dagger
//class Injection {
//    companion object {
//        fun provideRepository(application: Application): MovieRepository {
//            val database = MovieDatabase.getInstance(application)
//
//            val localRepository = LocalRepository.getInstance(database.movieDao())
//            val remoteRepository = RemoteRepository.getInstance()
//            val appExecutors = AppExecutors()
//            return MovieRepository.getInstance(localRepository, remoteRepository, appExecutors)
//        }
//    }
//}