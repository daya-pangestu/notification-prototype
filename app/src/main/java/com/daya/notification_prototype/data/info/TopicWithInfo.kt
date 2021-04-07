package com.daya.notification_prototype.data.info

import androidx.room.Embedded
import androidx.room.Relation

data class TopicWithInfo(
    @Embedded val topic : TopicEntity,
    @Relation(
        parentColumn = "topicId",
        entityColumn = "topicForeignId"
    )
    val News :List<InfoEntity>
)