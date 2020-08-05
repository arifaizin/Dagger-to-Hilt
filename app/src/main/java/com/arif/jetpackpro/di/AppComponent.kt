package com.arif.jetpackpro.di

import android.content.Context
import com.arif.jetpackpro.ui.detail.DetailActivity
import com.arif.jetpackpro.ui.favorite.FavoriteFragment
import com.arif.jetpackpro.ui.movie.MovieFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton




@Singleton
@Component(modules = [ViewModelModule::class, AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: DetailActivity)
    fun inject(fragment: MovieFragment)
    fun inject(fragment: FavoriteFragment)
}


