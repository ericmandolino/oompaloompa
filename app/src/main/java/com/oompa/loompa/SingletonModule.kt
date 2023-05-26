package com.oompa.loompa

import com.oompa.loompa.service.OompaLoompaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideOompaLoompaApiService(): OompaLoompaApiService = Retrofit.Builder()
        .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OompaLoompaApiService::class.java)
}