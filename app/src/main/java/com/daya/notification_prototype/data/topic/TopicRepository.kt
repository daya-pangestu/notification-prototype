package com.daya.notification_prototype.data.topic

import com.daya.notification_prototype.data.info.Topic
import javax.inject.Inject

class TopicRepository
@Inject
constructor(
    private val topicDataSource: TopicDataSource
) {

    suspend fun getDefaultTopic(): List<Topic> {
        return topicDataSource.getDefaultTopic()
    }

    fun subscribeTopic(topics: Topic) {
        topicDataSource.subscribeToTopic(topics)
    }

    fun unSubscribeTopic(topic: Topic) {
        topicDataSource.unsubscribeToTopic(topic)
    }

}