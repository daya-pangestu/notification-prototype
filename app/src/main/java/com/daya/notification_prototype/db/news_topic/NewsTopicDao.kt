package com.daya.notification_prototype.db.news_topic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoWithTopicRef
import com.daya.notification_prototype.data.topic.InfoWithTopic
import com.daya.notification_prototype.data.topic.TopicEntity
import com.daya.notification_prototype.data.topic.TopicWithInfo
import com.daya.notification_prototype.util.mapToTopicEntity
import com.daya.notification_prototype.util.mapper.TopicMapper.mapGeneralToEntity
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

    fun insertAllInfoEntity(listInfoEntity: List<InfoEntity>) {
        val topicEntity = listInfoEntity
            .map { info ->
                info
                    .topics
                    .mapGeneralToEntity()
            }
            .reduce { acc, list ->
                val combined = acc + list
                combined
            }
            .distinct()

        val infoTopicRef = listInfoEntity
            .map { info ->
                info
                    .topics
                    .map { topic ->
                        InfoWithTopicRef(info.senderId, topic.topicId)
                    }
            }.reduce { acc, list ->
                val combined = acc + list
                combined
            }
            .distinct()

        insertInfoEntityBatch(listInfoEntity)
        insertTopicEntityBatch(topicEntity)
        insertInfoTopicRefBatch(infoTopicRef)

    }
}