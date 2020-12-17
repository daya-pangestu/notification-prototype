package com.daya.notification_prototype.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daya.notification_prototype.db.news_topic.NewsEntity
import com.daya.notification_prototype.db.news_topic.NewsTopicDao
import com.daya.notification_prototype.db.news_topic.NewsWithTopicName
import com.daya.notification_prototype.db.news_topic.TopicEntity

@Database(
    entities = [
        NewsEntity::class,
        TopicEntity::class
    ],
    views = [
        NewsWithTopicName::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NotifDatabase : RoomDatabase() {
    abstract fun newsTopicDao() : NewsTopicDao
}