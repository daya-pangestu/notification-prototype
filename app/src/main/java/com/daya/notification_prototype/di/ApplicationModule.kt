package com.daya.notification_prototype.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import androidx.room.Room
import com.daya.notification_prototype.db.NotifDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

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

    @Provides
    @Singleton
    fun providesFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun providesMessaging() :FirebaseMessaging{
        return FirebaseMessaging.getInstance()
    }

    fun provideStorage() : FirebaseStorage{
        return FirebaseStorage.getInstance()
    }


}