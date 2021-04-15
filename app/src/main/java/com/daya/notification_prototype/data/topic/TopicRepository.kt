package com.daya.notification_prototype.data.topic

import javax.inject.Inject

class TopicRepository
@Inject
constructor(
    private val topicDataSource: FirebaseTopicDataSource
) {

    suspend fun getDefaultTopic(): List<TopicNet> {
        return topicDataSource.getDefaultTopic()
    }

    suspend fun subscribeTopic(topics: TopicNet): Boolean {
        return topicDataSource.subscribeToTopic(topics)
    }

    suspend fun unSubscribeTopic(topic: TopicNet): Boolean {
        return topicDataSource.unsubscribeToTopic(topic)
    }

}