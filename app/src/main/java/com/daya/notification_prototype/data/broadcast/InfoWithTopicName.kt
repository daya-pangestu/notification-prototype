package com.daya.notification_prototype.data.broadcast

import androidx.room.DatabaseView
import kotlin.String

@DatabaseView("SELECT news.senderId,news.title,news.description," +
        "news.urlAccess,news.urlImage,news.status," +
        "news.topicForeignId, topic.topicName FROM news " +
        "INNER JOIN topic ON topic.topicId = news.topicForeignId")
data class InfoWithTopicName(
    //from newsEntity
    val senderId: Int,
    val title: String,
    val description: String,
    val urlAccess: String,
    val urlImage: String? = null,
    val status: String,
    //from topicEntity
    val topicForeignId: Int,
    val topicName: String,
)
