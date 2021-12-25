package com.app.flowexample.di

import com.app.flowexample.data.remote.NetworkApi
import com.app.flowexample.data.repository.PostRepositoryImpl
import com.app.flowexample.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(networkApi: NetworkApi): PostRepository {
        return PostRepositoryImpl(networkApi)
    }

}