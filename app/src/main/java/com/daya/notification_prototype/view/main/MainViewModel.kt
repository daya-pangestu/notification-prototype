package com.daya.notification_prototype.view.main


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.domain.InfoPagingUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val infoPagingUseCase: InfoPagingUseCase
) : ViewModel() {

    val topicLiveData: MutableLiveData<List<Topic>> = MutableLiveData()

    init {
        setTopic()
    }

    private val _infoPagingFlow = infoPagingUseCase()
        .cachedIn(viewModelScope)

    fun infoPagingLiveData(): Flow<PagingData<Info>> {
        return _infoPagingFlow
    }

    private fun setTopic() {
        fireStore.collection("topics").get().addOnSuccessListener { querySnapshoot ->
            Timber.d(querySnapshoot.toString())
            val topics = querySnapshoot.documents.map {
                val topid = it.id
                val name = it.data?.get("topicName").toString()

                Topic(topicId = topid, topicName = name)
            }
            topicLiveData.value = topics
        }

    }


}
