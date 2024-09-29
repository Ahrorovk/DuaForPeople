package com.ahrorovk.duaforpeople.main.di

import com.ahrorovk.duaforpeople.main.data.network.MainRepositoryImpl
import com.ahrorovk.duaforpeople.main.domain.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMainRepository(): MainRepository =
        MainRepositoryImpl()
}