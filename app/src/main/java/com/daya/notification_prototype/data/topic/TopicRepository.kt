package com.daya.notification_prototype.data.topic

import javax.inject.Inject

class TopicRepository
@Inject
constructor(
    private val topicDataSource: TopicDataSource
) {

    suspend fun getDefaultTopic(): List<TopicNet> {
        return topicDataSource.getDefaultTopic()
    }

    fun subscribeTopic(topics: Topic) {
        topicDataSource.subscribeToTopic(topics)
    }

    fun unSubscribeTopic(topic: Topic) {
        topicDataSource.unsubscribeToTopic(topic)
    }

}