package com.daya.notification_prototype.data.broadcast.datasource

import com.daya.notification_prototype.data.broadcast.Info
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface BroadCastDataSource {
    suspend fun broadCastInfoWithoutImg(info : Info): Void?
    suspend fun broadCastInfoWithImg(info: Info): Void?

    suspend fun uploadingImage(uriImage : String)
}
class FirebaseBroadCastDataSource
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
) : BroadCastDataSource {
    override suspend  fun broadCastInfoWithoutImg(info: Info): Void? {
        return fireStore.collection("messages").document().set(info, SetOptions.merge()).await()
    }

    override suspend fun broadCastInfoWithImg(info: Info): Void? {
        return fireStore.collection("messages").document().set(info, SetOptions.merge()).await()
    }

    override suspend fun uploadingImage(uriImage: String) {

    }

}