package com.daya.notification_prototype

import android.app.Application
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.domain.IsFirstTimeUseCase
import com.daya.notification_prototype.domain.SetFirstTimeUseCase
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

    val mainScope = MainScope()

    @Inject
    lateinit var isFirstTimeUseCase : IsFirstTimeUseCase

    @Inject
    lateinit var setFirstTimeUseCase: SetFirstTimeUseCase

    @Inject
    lateinit var messaging : FirebaseMessaging

    @Inject
    lateinit var firestore: FirebaseFirestore

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        mainScope.launch {
            try {
                val isFirstTime = isFirstTimeUseCase()
                if (isFirstTime) {

                    val topicQuerySnap = firestore.collection("topics").get().await()
                    val topics = topicQuerySnap.documents.map {
                        val topid = it.id
                        val name = it.data?.get("topicName").toString()
                        Topic(topicId = topid, topicName = name)
                    }

                    topics.forEach {
                        messaging.subscribeToTopic(it.topicName).addOnCompleteListener { task ->
                            var msg = "subscring to ${it.topicName} succes"
                            if (!task.isSuccessful) {
                                msg = "subscribing to ${it.topicName} failed"
                            }
                            Timber.i(msg)
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
            } finally {
                setFirstTimeUseCase(false)
            }
        }
    }
}