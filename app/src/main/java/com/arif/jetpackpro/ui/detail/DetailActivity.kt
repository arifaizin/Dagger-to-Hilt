package com.arif.jetpackpro.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.arif.jetpackpro.BuildConfig
import com.arif.jetpackpro.MyApplication
import com.arif.jetpackpro.R
import com.arif.jetpackpro.data.entity.MovieModel
import com.arif.jetpackpro.di.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val detailMovieViewModel: DetailMovieViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
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
