package com.daya.notification_prototype.data.broadcast.datasource

import android.net.Uri
import com.daya.notification_prototype.data.broadcast.Info
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
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

    suspend fun broadCastWithOutImg(info: Info): Void? {
        return firebaseBroadCastDataSource.broadCastInfoWithoutImg(info)
    }

    suspend fun broadCastWithImg(info: Info): Task<Void> {
        return firebaseBroadCastDataSource.broadCastInfoWithImg(info)
    }

    fun getImageIstreamFromUri(uriImage: String): FileInputStream {
       return firebaseBroadCastDataSource.readFile(uriImage)
    }

}