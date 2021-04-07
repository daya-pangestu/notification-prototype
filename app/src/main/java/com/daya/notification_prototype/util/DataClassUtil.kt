package com.daya.notification_prototype.util

import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity

fun Info.mapToEntity(): InfoEntity {
    return InfoEntity(
        senderId = senderId,
        title = title,
        urlAccess = urlAccess,
        description = description,
        urlImage = urlImage,
        status = status,
        topics = topics,
        broadcastRequested = broadcastRequested
    )
}

fun InfoEntity.mapToPojo(): Info {
    return Info(
        senderId = senderId,
        title = title,
        urlAccess = urlAccess,
        description = description,
        urlImage = urlImage,
        status = status,
        topics = topics,
        broadcastRequested = broadcastRequested
    )
}