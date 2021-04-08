package com.daya.notification_prototype.data.topic

import com.daya.notification_prototype.data.info.Topic
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface TopicDataSource {
    suspend fun getDefaultTopic(): List<Topic>
    fun subscribeToTopic(topic : Topic)
    fun unsubscribeToTopic(topic: Topic)
}

class FirebaseTopicDataSource
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val messaging : FirebaseMessaging
) : TopicDataSource{
    override suspend fun getDefaultTopic(): List<Topic> {
        val querySnapshot = firestore.collection("topics").get().await()
        return querySnapshot.documents.asSequence().map {
            val topicId = it.id
            val name = it.data?.get("topicName").toString()
            Topic(topicId = topicId, topicName = name)
        }.toList()
    }

    override fun subscribeToTopic(topic: Topic){
        messaging.subscribeToTopic(topic.topicName)
            .addOnCompleteListener {task ->
                var msg = "subscribing to ${topic.topicName} success"
                if (!task.isSuccessful) {
                    msg = "subscribing to ${topic.topicName} failed"
                }
                Timber.i(msg)
            }
    }

    override fun unsubscribeToTopic(topic: Topic) {
        messaging.unsubscribeFromTopic(topic.topicName)
            .addOnCompleteListener {task ->
                var msg = "unsubscribed to ${topic.topicName} success"
                if (!task.isSuccessful) {
                    msg = "unsubscribed to ${topic.topicName} failed"
                }
                Timber.i(msg)
            }
    }

}













