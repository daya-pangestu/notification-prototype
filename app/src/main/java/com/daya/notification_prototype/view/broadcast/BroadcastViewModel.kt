package com.daya.notification_prototype.view.broadcast

import androidx.lifecycle.*
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.domain.broadcast.BroadCastInfoUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BroadcastViewModel
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val broadCastInfoUseCase: BroadCastInfoUseCase
) :ViewModel() {

    val topicLivedata: MutableLiveData<List<Topic>> = MutableLiveData()

    private val _broadcastInfoLiveData = MutableLiveData<Info>()

    val broadcastinfoLiveData = _broadcastInfoLiveData.switchMap { info ->
        liveData<Resource<Unit>> {
            val broadCastedRes = broadCastInfoUseCase(info)
            emitSource(broadCastedRes.asLiveData())
        }
    }

    init {
        setTopic()

    }

    private fun setTopic() {
         fireStore.collection("topics").get().addOnSuccessListener {querySnapshoot ->
            Timber.d(querySnapshoot.toString())
             val topics = querySnapshoot.documents.map {
                 val topid = it.id
                 val name = it.data?.get("topicName").toString()

                 Topic(topicId = topid, topicName = name )
             }
             topicLivedata.value = topics
        }.addOnFailureListener {
             Timber.d(it.localizedMessage)

         }
    }
    //TODO getTopic from firestore
    fun getTopic(): MutableLiveData<List<Topic>> {
        return topicLivedata
    }

    fun broadCastInfo(info: Info) {
        _broadcastInfoLiveData.value = info
    }

}

