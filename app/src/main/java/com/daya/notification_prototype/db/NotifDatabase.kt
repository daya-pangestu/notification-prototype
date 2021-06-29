package com.daya.notification_prototype.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.db.news_topic.NewsTopicDao
import com.daya.notification_prototype.data.info.InfoWithTopicRef
import com.daya.notification_prototype.data.topic.TopicEntity
import com.daya.notification_prototype.util.DateConverter

@Database(
    entities = [
        InfoEntity::class,
        TopicEntity::class,
        InfoWithTopicRef::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class NotifDatabase : RoomDatabase() {
    abstract fun newsTopicDao() : NewsTopicDao
}