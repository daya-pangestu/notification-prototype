package com.daya.notification_prototype.data.broadcast.datasource

import com.daya.notification_prototype.data.broadcast.Info
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import timber.log.Timber
import javax.inject.Inject

interface BroadCastDataSource {
    fun broadCastInfo(info : Info)
}
class FirebaseBroadCastDataSource
@Inject
constructor(
private val fireStore: FirebaseFirestore
) : BroadCastDataSource {
    override fun broadCastInfo(info: Info) {
        fireStore.collection("messages").document().set(info, SetOptions.merge())
            .addOnSuccessListener {
                Timber.i("DocumentSnapshot ${info.toString()} successfully written!")
            }
            .addOnFailureListener {e ->
                Timber.i("Error writing document $e" )
            }
    }

}