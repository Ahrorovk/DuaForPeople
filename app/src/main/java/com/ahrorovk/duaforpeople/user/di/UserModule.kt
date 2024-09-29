package com.ahrorovk.duaforpeople.user.di

import com.ahrorovk.duaforpeople.user.data.network.UserRepositoryImpl
import com.ahrorovk.duaforpeople.user.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository =
        UserRepositoryImpl()
}