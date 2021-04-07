package com.daya.notification_prototype.db.news_topic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.daya.notification_prototype.data.info.InfoWithTopicName
import com.daya.notification_prototype.data.info.TopicWithInfo

@Dao
interface NewsTopicDao {
    @Transaction
    @Query("SELECT * FROM topic")
     fun getTopicWithTheirNews(): LiveData<List<TopicWithInfo>>

    @Query("SELECT * from InfoWithTopicName")
     fun getAllNewsWithTopic() :LiveData<List<InfoWithTopicName>>

}