package com.daya.notification_prototype.data.broadcast

import androidx.room.*
import java.util.*
import kotlin.String

@Entity(tableName = "news")
data class InfoEntity(
    val senderId :String,
    val title : String,
    val description : String,
    val urlAccess : String,
    val urlImage : String? = null,
    val status : String,
    //define one to many relationship
    val topicForeignId :Int
)

data class Info(
    val senderId:String? = null,
    val title: String,
    val description: String,
    val urlAccess: String,
    val urlImage: String? = null,
    val status: String = "requested",
    val topics: List<String>,
    val broadcastRequested : Date
)

