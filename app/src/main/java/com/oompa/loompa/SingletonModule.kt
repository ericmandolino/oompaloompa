package com.oompa.loompa

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteQuery
import com.oompa.loompa.data.connectivity.ConnectivityMonitor
import com.oompa.loompa.data.connectivity.ConnectivityMonitorNetworkCallback
import com.oompa.loompa.data.database.OompaLoompaDatabase
import com.oompa.loompa.data.database.OompaLoompaQueryBuilder
import com.oompa.loompa.data.database.OompaLoompaSQLiteQueryBuilder
import com.oompa.loompa.data.database.OompaLoompasDao
import com.oompa.loompa.data.database.RemoteKeysDao
import com.oompa.loompa.data.service.OompaLoompaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideOompaLoompaApiService(): OompaLoompaApiService = Retrofit.Builder()
        .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OompaLoompaApiService::class.java)

    @Singleton
    @Provides
    fun provideOompaLoompaDatabase(@ApplicationContext context: Context): OompaLoompaDatabase =
        Room
            .databaseBuilder(context, OompaLoompaDatabase::class.java, "oompa_loompa_database")
            .build()

    @Singleton
    @Provides
    fun provideOompaLoompasDao(oompaLoompaDatabase: OompaLoompaDatabase): OompaLoompasDao =
        oompaLoompaDatabase.getOompaLoompasDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(oompaLoompaDatabase: OompaLoompaDatabase): RemoteKeysDao =
        oompaLoompaDatabase.getRemoteKeysDao()

    @Singleton
    @Provides
    fun provideOompaLoompaQueryBuilder() : OompaLoompaQueryBuilder<SupportSQLiteQuery> =
        OompaLoompaSQLiteQueryBuilder()

    @Singleton
    @Provides
    fun provideConnectivityMonitorNetworkCallback() : ConnectivityMonitorNetworkCallback = ConnectivityMonitorNetworkCallback()

    @Singleton
    @Provides
    fun provideConnectivityMonitor(connectivityMonitor: ConnectivityMonitorNetworkCallback) : ConnectivityMonitor = connectivityMonitor
}