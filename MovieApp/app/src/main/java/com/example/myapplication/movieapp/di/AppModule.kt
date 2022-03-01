package com.example.myapplication.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.movieapp.data.db.MoviesDB
import com.example.myapplication.movieapp.data.db.dao.PlayListDao
import com.example.myapplication.movieapp.data.network.MoviesAPI
import com.example.myapplication.movieapp.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [ServiceModule::class])
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun providePlayListRepository(impl: PlayListRepository): IPlayListRepository

    @Binds
    @Singleton
    abstract fun provideMovieRepository(impl: MoviesRepository): IMovieRepository

    @Binds
    @Singleton
    abstract fun providesMovieRepoManager(impl: MovieRepoManager): IMovieRepoManager

}

@InstallIn(SingletonComponent::class)
@Module
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideUserApi(): MoviesAPI {
        return Retrofit.Builder()
            .baseUrl(MoviesAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesAPI::class.java)
    }

    @Provides
    @Singleton
    fun providePlayListDao(movieDB: MoviesDB): PlayListDao {
        return movieDB.playListDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MoviesDB {
        return Room.databaseBuilder(
            appContext,
            MoviesDB::class.java,
            "MoviesDB"
        )
            .build()
    }
}