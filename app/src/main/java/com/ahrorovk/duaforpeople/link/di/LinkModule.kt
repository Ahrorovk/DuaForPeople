package com.ahrorovk.duaforpeople.link.di

import com.ahrorovk.duaforpeople.link.data.network.LinkRepositoryImpl
import com.ahrorovk.duaforpeople.link.domain.LinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LinkModule {

    @Singleton
    @Provides
    fun provideLinkRepository(
    ): LinkRepository = LinkRepositoryImpl()
}