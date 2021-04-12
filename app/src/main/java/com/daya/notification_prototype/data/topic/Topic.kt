package com.daya.notification_prototype.data.topic

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String


data class TopicNet(
    val topicId : String,
    val topicName : String
)

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = false)
    val topicId :String,
    val topicName : String,
)

data class Topic(
    val topicId : String,
    val topicName : String,
    //val isUserSubscribed: Boolean //TODO ini ter reference di user
)


