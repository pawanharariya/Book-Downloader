package com.psh.assignment.di

import com.psh.assignment.data.remote.RemoteAPI
import com.psh.assignment.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL).build()
    }

    @Singleton
    @Provides
    fun providesRemoteAPI(retrofit: Retrofit): RemoteAPI {
        return retrofit.create(RemoteAPI::class.java)
    }
}