package com.daya.notification_prototype.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferenceModule {

    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}