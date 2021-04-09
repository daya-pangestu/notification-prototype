package com.daya.notification_prototype.data.info

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore
import com.daya.notification_prototype.data.topic.Topic
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import kotlin.String

@Entity(tableName = "info")
data class InfoEntity(
        @PrimaryKey(autoGenerate = false)
        var senderId: String = "",
        val title: String = "",
        val description: String = "",
        val urlAccess: String = "",
        var urlImage: String = "",
        val status: String = "",
        val broadcastRequested: Date? = null
)

data class Info(
    var senderId: String = "",
    val title: String  = "",
    val description: String = "",
    val urlAccess: String = "",
    var urlImage: String = "",
    val status: String = "requested",
    val topics: List<Topic> = emptyList(),
    @ServerTimestamp
    val broadcastRequested: Date? = null
)