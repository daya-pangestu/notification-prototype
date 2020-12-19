package com.daya.notification_prototype.view.broadcast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daya.notification_prototype.data.broadcast.Info
import com.daya.notification_prototype.data.broadcast.Topic
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import timber.log.Timber

class BroadcastViewModel
@ViewModelInject
constructor(
        val fireStore: FirebaseFirestore
) :ViewModel() {

    val topicLivedata: MutableLiveData<List<Topic>> = MutableLiveData()
    val subscribing : MutableLiveData<Result<Unit>> = MutableLiveData()

    init {
        setTopic()
    }

    private fun setTopic() {
/*        topicLivedata.value = arrayListOf(
            "umum",
            "Mata Kuliab",
            "Laboratorium"
        )*/

         fireStore.collection("topics").get().addOnSuccessListener {querySnapshoot ->
            Timber.d(querySnapshoot.toString())
             val topics = querySnapshoot.documents.map {
                 val topid = it.id
                 val name = it.data?.get("topicName").toString()

                 Topic(topicId = topid, topicName = name )
             }
             topicLivedata.value = topics
        }
    }
    //TODO getTopic from firestore
    fun getTopic(): MutableLiveData<List<Topic>> {
        return topicLivedata
    }

    fun broadCastInfo(info: Info) {

        fireStore.collection("messages").document().set(info, SetOptions.merge())
            .addOnSuccessListener {
                Timber.i("DocumentSnapshot ${info.toString()} successfully written!")
            }
            .addOnFailureListener {e ->
                Timber.i("Error writing document $e" )
            }
    }

}

