package com.arif.daggerhilt.data.entity

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<MovieModel>? = java.util.ArrayList(),

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)