package com.daya.notification_prototype.data.info

import androidx.room.*
import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.String

@Entity(tableName = "news")
data class InfoEntity(
    @PrimaryKey(autoGenerate = false)
    var senderId :String,
    val title : String,
    val description : String,
    val urlAccess : String,
    var urlImage : String = "",
    val status : String,
    val topics: List<String>,
    val broadcastRequested: Date? = null
)

data class Info(
    val senderId: String = "",
    val title: String,
    val description: String,
    val urlAccess: String,
    var urlImage: String = "",
    val status: String = "requested",
    val topics: List<String>,
    @ServerTimestamp
    val broadcastRequested: Date? = null
)