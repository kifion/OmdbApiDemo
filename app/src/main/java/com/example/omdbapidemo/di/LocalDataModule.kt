package com.example.omdbapidemo.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    companion object {
        const val NAME_USER_PREFERENCES = "user_preference"
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(NAME_USER_PREFERENCES, Context.MODE_PRIVATE)
    }
}