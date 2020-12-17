package com.daya.notification_prototype.db.news_topic

import androidx.room.*

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val senderId :Int,
    val title : String,
    val description :String,
    val urlAccess : String,
    val urlImage : String? = null,
    val status : String,
    //define one to many relationship
    val topicForeignId :Int
)

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = true)
    val topicId :Int,
    val topicName :String,
)

data class TopicWithNews(
    @Embedded val topic : TopicEntity,
    @Relation(
        parentColumn = "topicId",
        entityColumn = "topicForeignId"
    )
    val News :List<NewsEntity>
)

@DatabaseView("SELECT news.senderId,news.title,news.description," +
        "news.urlAccess,news.urlImage,news.status," +
        "news.topicForeignId, topic.topicName FROM news " +
        "INNER JOIN topic ON topic.topicId = news.topicForeignId")
data class NewsWithTopicName(
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

