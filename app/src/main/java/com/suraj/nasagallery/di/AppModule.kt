package com.suraj.nasagallery.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import com.suraj.nasagallery.data.DefaultImageRepository
import com.suraj.nasagallery.data.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideImageRepository(context: Context, moshi: Moshi): ImageRepository =
        DefaultImageRepository(context, moshi)
}
