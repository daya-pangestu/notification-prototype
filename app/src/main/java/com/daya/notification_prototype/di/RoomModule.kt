package com.daya.notification_prototype.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import androidx.room.Room
import com.daya.notification_prototype.db.NotifDatabase
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): NotifDatabase {
        return Room
            .databaseBuilder(
                context,
                NotifDatabase::class.java,
                "notif-db"
            )
            .build()
    }
}