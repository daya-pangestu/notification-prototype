package com.daya.notification_prototype.data.info

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    val topicId :Int,
    val topicName : String,
)

data class Topic(
    val topicId : String,
    val topicName : String,
    //val isUserSubscribed: Boolean //TODO ini ter reference di user
)


