package com.daya.notification_prototype.view.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daya.notification_prototype.data.broadcast.Topic
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class MainViewModel
@ViewModelInject
constructor(
        val fireStore: FirebaseFirestore
) : ViewModel() {

    val topicLivedata: MutableLiveData<List<Topic>> = MutableLiveData()

    init {
        setTopic()
    }

    private fun setTopic() {
        fireStore.collection("topics").get().addOnSuccessListener { querySnapshoot ->
            Timber.d(querySnapshoot.toString())
            val topics = querySnapshoot.documents.map {
                val topid = it.id
                val name = it.data?.get("topicName").toString()

                Topic(topicId = topid, topicName = name)
            }
            topicLivedata.value = topics
        }

    }

}
