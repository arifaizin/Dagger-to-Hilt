package com.arif.daggerhilt.data.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieModel(
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	var id: Int? = null,

	@ColumnInfo(name = "title")
	@field:SerializedName("title")
	var title: String? = null,

	@ColumnInfo(name = "overview")
	@field:SerializedName("overview")
	var overview: String? = null,

	@ColumnInfo(name = "posterPath")
	@field:SerializedName("poster_path")
	var posterPath: String? = null,

	@ColumnInfo(name = "backdropPath")
	@field:SerializedName("backdrop_path")
	var backdropPath: String? = null,

	@ColumnInfo(name = "releaseDate")
	@field:SerializedName("release_date")
	var releaseDate: String? = null,

	@ColumnInfo(name = "isFavorite")
	var isFavorite: Boolean = false
) : Parcelable