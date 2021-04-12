package com.daya.notification_prototype.data.broadcast.datasource

import com.daya.notification_prototype.data.info.InfoNet
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import java.io.FileInputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BroadcastRepository
@Inject
constructor(
   private val firebaseBroadCastDataSource: FirebaseBroadCastDataSource
) {

    fun addChild(filename: String): StorageReference {
        return firebaseBroadCastDataSource.addChild(filename)
    }

    suspend fun broadCastWithOutImg(info: InfoNet): Void? {
        return firebaseBroadCastDataSource.broadCastInfoWithoutImg(info)
    }

    suspend fun broadCastWithImg(info: InfoNet): Task<Void> {
        return firebaseBroadCastDataSource.broadCastInfoWithImg(info)
    }

    fun getImageIstreamFromUri(uriImage: String): FileInputStream {
       return firebaseBroadCastDataSource.readFile(uriImage)
    }

}