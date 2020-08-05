package com.arif.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.arif.jetpackpro.data.source.remote.ApiResponse
import com.arif.jetpackpro.data.source.remote.StatusResponse
import com.arif.jetpackpro.util.AppExecutors


abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.Loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(
                    Resource.Success(
                        newData
                    )
                ) }
            }
        }
    }

    private fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall() as LiveData<ApiResponse<RequestType>>

        result.addSource(dbSource) { newData -> result.setValue(
            Resource.Loading(
                newData
            )
        ) }
        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                StatusResponse.SUCCESS -> mExecutors.diskIO().execute {
                    saveCallResult(response.body)
                    mExecutors.mainThread().execute {
                        result.addSource(loadFromDB()) { newData -> result.setValue(
                            Resource.Success(newData)
                        ) }
                    }
                }
                StatusResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData -> result.setValue(
                        Resource.Success(
                            newData
                        )
                    ) }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData -> result.setValue(
                        Resource.Error(
                            response.message.toString(),
                            newData
                        )
                    ) }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}