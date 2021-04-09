package com.daya.notification_prototype.data.topic

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoWithTopicRef

data class TopicWithInfo(
        @Embedded val topic: TopicEntity,
        @Relation(
                parentColumn = "topicId",
                entityColumn = "senderId",
                associateBy = Junction(InfoWithTopicRef::class)
        )
        val info: List<InfoEntity>
)

data class InfoWithTopic(
        @Embedded val info : InfoEntity,
        @Relation(
                parentColumn = "senderId",
                entityColumn = "topicId",
                associateBy = Junction(InfoWithTopicRef::class)
        )
        val topics : List<TopicEntity>

)