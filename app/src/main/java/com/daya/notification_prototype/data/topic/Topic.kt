package com.daya.notification_prototype.data.topic

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlin.String


data class TopicNet(
    val topicId : String,
    val topicName : String,
    var isUserSubscribed: Boolean = false,
    var isUnsubscribeAble : Boolean = false
)

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = false)
    val topicId :String,
    val topicName : String,
)
@Parcelize
data class Topic(
        val topicId: String,
        val topicName: String,
        val isUserSubscribe: Boolean = false,//TODO ini ter reference di user
        val isUnsubscribeAble: Boolean = false

) : Parcelable


