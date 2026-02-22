package com.biblinder.di

import android.content.Context
import androidx.room.Room
import com.biblinder.data.api.JikanApiService
import com.biblinder.data.api.JikanRepository
import com.biblinder.data.local.AnimeDao
import com.biblinder.data.local.BiblinderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): JikanApiService =
        retrofit.create(JikanApiService::class.java)

    @Provides @Singleton
    fun provideDatabase(app: Context): BiblinderDatabase =
        Room.databaseBuilder(app, BiblinderDatabase::class.java, "biblinder.db").build()

    @Provides fun provideDao(db: BiblinderDatabase): AnimeDao = db.animeDao()

    @Provides @Singleton
    fun provideRepository(api: JikanApiService) = JikanRepository(api)
}
