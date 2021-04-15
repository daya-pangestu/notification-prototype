package com.daya.notification_prototype.util

import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.data.topic.TopicEntity
import com.daya.notification_prototype.data.topic.TopicNet


fun Topic.mapToTopicEntity(): TopicEntity {
    return TopicEntity(
        topicId = topicId,
        topicName = topicName
    )
}

fun Topic.mapToTopicNet(): TopicNet {
    return TopicNet(
            topicId = topicId,
            topicName = topicName,
            isUserSubscribed = isUserSubscribe,
            isUnsubscribeAble = isUnsubscribeAble
    )
}