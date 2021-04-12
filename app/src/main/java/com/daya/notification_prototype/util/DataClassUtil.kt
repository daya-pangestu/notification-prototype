package com.daya.notification_prototype.util

import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.data.topic.TopicEntity


fun Topic.mapToTopicEntity(): TopicEntity {
    return TopicEntity(
        topicId = topicId,
        topicName = topicName
    )
}