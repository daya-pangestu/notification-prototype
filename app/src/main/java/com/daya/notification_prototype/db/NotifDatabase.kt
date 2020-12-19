package com.daya.notification_prototype.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daya.notification_prototype.data.broadcast.InfoEntity
import com.daya.notification_prototype.db.news_topic.NewsTopicDao
import com.daya.notification_prototype.data.broadcast.InfoWithTopicName
import com.daya.notification_prototype.data.broadcast.TopicEntity

@Database(
    entities = [
        InfoEntity::class,
        TopicEntity::class
    ],
    views = [
        InfoWithTopicName::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NotifDatabase : RoomDatabase() {
    abstract fun newsTopicDao() : NewsTopicDao
}