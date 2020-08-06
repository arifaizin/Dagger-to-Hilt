package com.arif.daggerhilt.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.arif.daggerhilt.BuildConfig
import com.arif.daggerhilt.R
import com.arif.daggerhilt.data.entity.MovieModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailMovieViewModel: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.hide()

//        Before Using Dagger
//        val factory = ViewModelFactory.getInstance(this)
//        detailMovieViewModel = ViewModelProviders.of(this, factory).get(DetailMovieViewModel::class.java)

        val dataMovie = intent.getParcelableExtra<MovieModel>("data")
        if (dataMovie != null) {
            showDetailMovie(dataMovie)
        }
    }

    private fun showDetailMovie(dataMovie: MovieModel) {
        detailTextTitle.text = dataMovie.title
        detailTextRelease.text = dataMovie.releaseDate
        detailTextOverview.text = dataMovie.overview

        Glide.with(this).load(BuildConfig.POSTER_URL + dataMovie.posterPath).into(detailImagePoster)
        Glide.with(this).load(BuildConfig.POSTER_URL + dataMovie.backdropPath)
            .into(detailImageHeader)

        var statusFavorite = dataMovie.isFavorite
        setStatusFavorite(statusFavorite)
        detailButtonFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            detailMovieViewModel.setFavoriteMovie(dataMovie, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            detailButtonFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_favorite
                )
            )
        } else {
            detailButtonFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_action_not_favorite
                )
            )
        }
    }

}
