package com.daya.notification_prototype.data.info

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore
import com.daya.notification_prototype.data.topic.Topic
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import kotlin.String

// info come from network
data class InfoNet(
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

//persist info with this
@Entity(tableName = "info")
data class InfoEntity
(
    @PrimaryKey(autoGenerate = false)
    var senderId: String = "",
    val title: String = "",
    val description: String = "",
    val urlAccess: String = "",
    var urlImage: String = "",
    val status: String = "",
    val broadcastRequested: Date? = null,
    @Ignore
    var topics: List<Topic>
){
    //second constructor so ignore would work see :
    constructor(senderId: String,title: String,description: String,urlAccess: String,urlImage: String,status: String,broadcastRequested: Date?)
            : this(senderId, title, description, urlAccess, urlImage, status, broadcastRequested, emptyList())
}
//general info to display in ui
data class Info(
    var senderId: String = "",
    val title: String  = "",
    val description: String = "",
    val urlAccess: String = "",
    var urlImage: String = "",
    val status: String = "requested",
    val topics: List<Topic> = emptyList(),
    val broadcastRequested: Date? = null
)