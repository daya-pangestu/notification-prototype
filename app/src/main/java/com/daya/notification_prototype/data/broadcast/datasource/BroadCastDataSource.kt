package com.daya.notification_prototype.data.broadcast.datasource

import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.broadcast.Info
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface BroadCastDataSource {
    suspend fun broadCastInfo(info : Info)
}
class FirebaseBroadCastDataSource
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
) : BroadCastDataSource {
    override suspend  fun broadCastInfo(info: Info) {
         fireStore.collection("messages").document().set(info, SetOptions.merge())

    }

    private suspend fun uploadImage(uriImage: String) {

    }
}