package com.daya.notification_prototype.db.news_topic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoWithTopicRef
import com.daya.notification_prototype.data.topic.InfoWithTopic
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.data.topic.TopicEntity
import com.daya.notification_prototype.data.topic.TopicWithInfo
import com.daya.notification_prototype.util.mapToEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NewsTopicDao {
    @Transaction
    @Query("SELECT * FROM topic")
    abstract fun getTopicWithTheirInfo(): Flow<List<TopicWithInfo>>

    @Query("SELECT * from info")
    abstract fun getAllNewsWithTopic(): Flow<List<InfoWithTopic>>

    @Insert
    abstract fun insertInfoEntityBatch(listInfo: List<InfoEntity>)

    @Insert
    abstract fun insertTopicEntityBatch(listTopic: List<TopicEntity>)

    
    @Insert
    abstract fun insertInfoTopicRefBatch(listInfoTopicRef: List<InfoWithTopicRef>)

    fun insertInfoBatch(listInfo: List<Info>) {
        val infoEntity = listInfo.map { it.mapToEntity() }
        val topicEntity = listInfo.map {
            it.topics
        }.reduce { acc, list ->
            val combined = acc + list
            combined
        }.distinct()
                .map {
                    TopicEntity(
                            topicId = it.topicId,
                            topicName = it.topicName
                    )
                }



        val infoTopicRef = listInfo
                .map { info ->
                    val list = info.topics
                            .map {topic ->
                                InfoWithTopicRef(
                                        senderId = info.senderId,
                                        topicId = topic.topicId
                                )
                            }
                    list
                }.reduce { acc, list ->
                    val combined = acc + list
                    combined
                }
                .distinct()
        insertInfoEntityBatch(infoEntity)
        insertTopicEntityBatch(topicEntity)
        insertInfoTopicRefBatch(infoTopicRef)

    }

}