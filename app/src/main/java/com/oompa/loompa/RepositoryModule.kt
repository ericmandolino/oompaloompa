package com.oompa.loompa

import com.oompa.loompa.data.OompaLoompaRepositoryImpl
import com.oompa.loompa.viewmodel.OompaLoompaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindOompaLoompaRepository(
        oompaLoompaRepositoryImpl: OompaLoompaRepositoryImpl
    ): OompaLoompaRepository
}