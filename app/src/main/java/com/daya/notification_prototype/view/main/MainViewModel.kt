package com.daya.notification_prototype.view.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daya.notification_prototype.data.broadcast.Topic
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val fireStore: FirebaseFirestore
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
