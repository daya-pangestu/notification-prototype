package com.daya.notification_prototype.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Provides
import javax.inject.Singleton

object FirebaseModule {
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
    fun providesMessaging() : FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }

    @Provides
    @Singleton
    fun provideStorage() : FirebaseStorage {
        return Firebase.storage
    }
}