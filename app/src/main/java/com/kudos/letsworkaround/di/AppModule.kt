package com.kudos.letsworkaround.di

import com.kudos.letsworkaround.network.service.DevBytesApiService
import com.kudos.letsworkaround.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMainRepository(devBytesApiService: DevBytesApiService): MainRepository {
        return MainRepository(devBytesApiService)
    }

}