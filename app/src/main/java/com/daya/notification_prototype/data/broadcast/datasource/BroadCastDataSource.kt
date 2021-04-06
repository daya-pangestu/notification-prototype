package com.daya.notification_prototype.data.broadcast.datasource

import android.net.Uri
import com.daya.notification_prototype.data.broadcast.Info
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

interface BroadCastDataSource {

    suspend fun broadCastInfoWithoutImg(info : Info): Void?

    fun addChild(filename :String): StorageReference
    fun readFile(uriImage: String): FileInputStream
    suspend fun broadCastInfoWithImg(info: Info): Task<Void>

}
class FirebaseBroadCastDataSource
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val imageRef : StorageReference
) : BroadCastDataSource {
    override suspend  fun broadCastInfoWithoutImg(info: Info): Void? {
        return fireStore.collection("messages").document().set(info, SetOptions.merge()).await()
    }


    override fun addChild(filename: String): StorageReference {
       return imageRef.child(filename)
    }

    override fun readFile(uriImage: String): FileInputStream {
        return FileInputStream(File(uriImage))
    }


    override suspend fun broadCastInfoWithImg(info: Info): Task<Void> {
        return fireStore.collection("messages").document().set(info, SetOptions.merge())
    }
}