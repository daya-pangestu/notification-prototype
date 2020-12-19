package com.daya.notification_prototype.data.broadcast

import androidx.room.*
import kotlin.String

@Entity(tableName = "news")
data class InfoEntity(
    @PrimaryKey(autoGenerate = true)
    val senderId :Int,
    val title : kotlin.String,
    val description : kotlin.String,
    val urlAccess : kotlin.String,
    val urlImage : kotlin.String? = null,
    val status : kotlin.String,
    //define one to many relationship
    val topicForeignId :Int
)

data class Info(
    val senderId:Int? = null,
    val title: String,
    val description: String,
    val urlAccess: String,
    val urlImage: String? = null,
    val status: String = "requested",
    val topics: List<String>
)

