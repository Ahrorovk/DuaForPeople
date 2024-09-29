package com.ahrorovk.duaforpeople.senderRequest.di

import com.ahrorovk.duaforpeople.core.util.Constants
import com.ahrorovk.duaforpeople.senderRequest.data.network.SenderRequestRepositoryImpl
import com.ahrorovk.duaforpeople.senderRequest.data.network.remote.SenderRequestApi
import com.ahrorovk.duaforpeople.senderRequest.domain.SenderRequestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SenderRequestModule {

    @Singleton
    @Provides
    fun provideSenderRequestApi(): SenderRequestApi =
        Retrofit
            .Builder()
            .baseUrl(Constants.FCM_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(150, TimeUnit.SECONDS)
                    .connectTimeout(150, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(SenderRequestApi::class.java)

    @Singleton
    @Provides
    fun provideSenderRequestRepository(api: SenderRequestApi): SenderRequestRepository =
        SenderRequestRepositoryImpl(api)
}