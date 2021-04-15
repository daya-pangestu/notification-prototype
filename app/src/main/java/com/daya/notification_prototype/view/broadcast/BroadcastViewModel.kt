package com.daya.notification_prototype.view.broadcast

import androidx.lifecycle.*
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.domain.broadcast.BroadCastInfoUseCase
import com.daya.notification_prototype.domain.broadcast.GetAllTopicUseCase
import com.daya.notification_prototype.domain.settings.GetTopicAndSubscribedStatusUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BroadcastViewModel
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val broadCastInfoUseCase: BroadCastInfoUseCase,
    private val getAllTopicUseCase: GetAllTopicUseCase
) :ViewModel() {

    private val _broadcastInfoLiveData = MutableLiveData<Info>()
    fun broadCastInfo(info: Info) {
        _broadcastInfoLiveData.value = info
    }
    val broadcastingLiveData = _broadcastInfoLiveData.switchMap { info ->
        liveData {
            val broadCastedRes = broadCastInfoUseCase(info)
            emitSource(broadCastedRes.asLiveData())
        }
    }

    private val _getAllDefaultTopic = liveData {
        emit(Resource.loading())
        val listTopic = getAllTopicUseCase(Unit)
        emit(listTopic)
    }
    fun getTopic(): LiveData<Resource<List<Topic>>> {
        return _getAllDefaultTopic
    }
}

