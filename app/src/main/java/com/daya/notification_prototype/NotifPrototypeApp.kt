package com.daya.notification_prototype

import android.app.Application
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.domain.pref.IsFirstTimeUseCase
import com.daya.notification_prototype.domain.pref.SetFirstTimeUseCase
import com.daya.notification_prototype.domain.settings.SubscribeTopicUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

import timber.log.Timber.DebugTree
import java.lang.Exception
import javax.inject.Inject

@HiltAndroidApp
class NotifPrototypeApp : Application() {

    private val mainScope = MainScope()

    @Inject
    lateinit var isFirstTimeUseCase : IsFirstTimeUseCase

    @Inject
    lateinit var setFirstTimeUseCase: SetFirstTimeUseCase

    @Inject
    lateinit var subscribeTopicUseCase: SubscribeTopicUseCase

    @Inject
    lateinit var messaging : FirebaseMessaging

    @Inject
    lateinit var firestore: FirebaseFirestore

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
        mainScope.launch {
            val isFirstTime = isFirstTimeUseCase()
            if (isFirstTime) {
                try {
                    val topicQuerySnap = firestore.collection("topics").get().await()
                    val topics = topicQuerySnap.documents.map {
                        val topid = it.id
                        val name = it.data?.get("topicName").toString()
                        Topic(topicId = topid, topicName = name)
                    }
                    topics.forEach {
                        subscribeTopicUseCase(it)
                    }
                } catch (e: Exception) {
                    Timber.e(e.localizedMessage)
                } finally {
                    setFirstTimeUseCase(false)
                }
            }
        }
    }
}