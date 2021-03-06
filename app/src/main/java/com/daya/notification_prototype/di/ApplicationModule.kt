package com.daya.notification_prototype.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import androidx.room.Room
import com.daya.notification_prototype.db.NotifDatabase
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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

//    @Provides
//    fun providesNewsStore(firestoreDataSource : FirestoreDataSource, db :NotifDatabase ){
//        return StoreBuilder
//            .from(
//                Fetcher.of {
//                    firestoreDataSource()
//                },
//                sourceOfTruth = SourceOfTruth.of(
//                    reader      = db.newsTopicDao()::getAllNewsWithTopic,
//                    writer      = db.newsTopicDao()::insertNews,
//                    delete      = db.newsTopicDao()::deleteNews,
//                    deleteAll   = db.newsTopicDao()::deleteAllNews,
//
//                )
//            ).build()
//    }

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


}