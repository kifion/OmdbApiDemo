package com.example.omdbapidemo.di

import android.content.SharedPreferences
import com.example.omdbapidemo.data.local.UserPreferenceDataSource
import com.example.omdbapidemo.data.remote.api.OmdbApiService
import com.example.omdbapidemo.domain.repository.NetworkRepository
import com.example.omdbapidemo.data.remote.NetworkRepositoryDataSource
import com.example.omdbapidemo.domain.repository.UserPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(apiService: OmdbApiService): NetworkRepository {
        return NetworkRepositoryDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(sharedPreferences: SharedPreferences): UserPreferenceRepository {
        return UserPreferenceDataSource(sharedPreferences)
    }
}