package com.daya.notification_prototype.data.topic

import com.daya.notification_prototype.di.FirebaseApiService
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.okhttp.ResponseBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface TopicDataSource {
    suspend fun getDefaultTopic(): List<TopicNet>
    fun subscribeToTopic(topic: Topic)
    fun unsubscribeToTopic(topic: Topic)
    suspend fun getSubscribedTopic(): List<TopicNet>

}

class FirebaseTopicDataSource
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val messaging: FirebaseMessaging,
    private val firebaseService: FirebaseApiService,
    private val firebaseUser: FirebaseUser?
) : TopicDataSource {
    override suspend fun getDefaultTopic(): List<TopicNet> {
        val querySnapshot = firestore.collection("topics").get().await()
        return querySnapshot.documents.asSequence().map {
            val topicId = it.id
            val name = it.data?.get("topicName").toString()
            TopicNet(topicId = topicId, topicName = name)
        }.toList()
    }

    override fun subscribeToTopic(topic: Topic) {
        messaging.subscribeToTopic(topic.topicName)
            .addOnCompleteListener { task ->
                var msg = "subscribing to ${topic.topicName} success"
                if (!task.isSuccessful) {
                    msg = "subscribing to ${topic.topicName} failed"
                }
                Timber.i(msg)
            }
    }

    override fun unsubscribeToTopic(topic: Topic) {
        messaging.unsubscribeFromTopic(topic.topicName)
            .addOnCompleteListener { task ->
                var msg = "unsubscribed to ${topic.topicName} success"
                if (!task.isSuccessful) {
                    msg = "unsubscribed to ${topic.topicName} failed"
                }
                Timber.i(msg)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getSubscribedTopic(): List<TopicNet> =
        suspendCancellableCoroutine { continuation ->





            var client: Call<String>? = null
            messaging.token
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val token = it.result!!
                        client = firebaseService.getlistSubscribedTopic(token)
                        client?.enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                val body = response.body()
                                Timber.i("response $body")
                                continuation.resume(emptyList())
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                continuation.resumeWithException(t)
                            }
                        })
                    } else {
                        continuation.resumeWithException(it.exception!!)
                    }
                }
            continuation.invokeOnCancellation {
                client?.cancel()
            }
        }
}




/**
* current response for subscribed topic
{
"applicationVersion":"1",
"application":"com.daya.notification_prototype",
"scope":"*",
"authorizedEntity":"940399138392",
"rel":{
"topics":{
"laboratorium":{
"addDate":"2021-04-13"
},
"umum":{
"addDate":"2021-04-13"
},
"jadwal_Kuliah":{
"addDate":"2021-04-13"
}
}
},
"appSigner":"f8f8f1bdbed986c099dc7ff6c671ada474420bd2",
"platform":"ANDROID"
}
* */








