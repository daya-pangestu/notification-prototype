package com.daya.notification_prototype.util.mapper

import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.data.topic.TopicEntity
import com.daya.notification_prototype.data.topic.TopicNet

object TopicMapper : Mapper<Topic,TopicNet,TopicEntity> {
    override fun List<Topic>.mapGeneralToNet(): List<TopicNet> {
       return this.map {
            TopicNet(
                topicId = it.topicId,
                topicName = it.topicName
            )
        }
    }

    override fun List<Topic>.mapGeneralToEntity(): List<TopicEntity> {
        return this.map {
            TopicEntity(
                topicId = it.topicId,
                topicName = it.topicName
            )
        }    }

    override fun List<TopicNet>.mapNetToGeneral(): List<Topic> {
        return this.map {
            Topic(
                topicId = it.topicId,
                topicName = it.topicName
            )
        }
    }

    override fun List<TopicNet>.mapNetToEntity(): List<TopicEntity> {
        return this.map {
            TopicEntity(
                topicId = it.topicId,
                topicName = it.topicName
            )
        }
    }

    override fun List<TopicEntity>.mapEntityToGeneral(): List<Topic> {
        return this.map {
            Topic(
                topicId = it.topicId,
                topicName = it.topicName
            )
        }
    }

    override fun List<TopicEntity>.mapEntityToNet(): List<TopicNet> {
        return this.map {
            TopicNet(
                topicId = it.topicId,
                topicName = it.topicName
            )
        }
    }
}