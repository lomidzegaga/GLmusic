package com.example.glmusic.di

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.glmusic.data.remote.RemoteDataProvider
import com.example.glmusic.data.repositoryImpl.TrackRepositoryImpl
import com.example.glmusic.domain.repository.TrackRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun addClientIdQueryParam() = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val updatedUrl = originalUrl
            .newBuilder()
            .addQueryParameter("client_id", "6f28ba8d")
            .build()

        val updatedRequest = originalRequest
            .newBuilder()
            .url(updatedUrl)
            .build()

        chain.proceed(updatedRequest)
    }


    @Singleton
    @Provides
    fun provideOkhttpClient(interceptor: Interceptor) = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()


    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.jamendo.com/")
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RemoteDataProvider {
        return retrofit.create(RemoteDataProvider::class.java)
    }

    @Singleton
    @Provides
    fun provideTrackRepository(remoteDataProvider: RemoteDataProvider): TrackRepository {
        return TrackRepositoryImpl(remoteDataProvider)
    }

    @Singleton
    @Provides
    fun providePlayer(@ApplicationContext context: Context): Player {
        return ExoPlayer.Builder(context).build()
    }
}