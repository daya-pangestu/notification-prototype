package com.daya.notification_prototype.db.news_topic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsTopicDao {
    @Transaction
    @Query("SELECT * FROM topic")
     fun getTopicWithTheirNews(): LiveData<List<TopicWithNews>>

    @Query("SELECT * from NewsWithTopicName")
     fun getAllNewsWithTopic() :LiveData<List<NewsWithTopicName>>

}