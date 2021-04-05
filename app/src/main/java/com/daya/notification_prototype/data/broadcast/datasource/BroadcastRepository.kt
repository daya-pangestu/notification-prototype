package com.daya.notification_prototype.data.broadcast.datasource

import com.daya.notification_prototype.data.broadcast.Info
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BroadcastRepository
@Inject
constructor(
   private val firebaseBroadCastDataSource: FirebaseBroadCastDataSource
) {

    suspend fun broadCastWithOutImg(info: Info): Void? {
        return firebaseBroadCastDataSource.broadCastInfoWithoutImg(info)
    }

    suspend fun broadCastWithImg(info: Info): Void? {
        return firebaseBroadCastDataSource.broadCastInfoWithImg(info)
    }

    suspend fun uploadingImg(uriImage: String) {

    }

}