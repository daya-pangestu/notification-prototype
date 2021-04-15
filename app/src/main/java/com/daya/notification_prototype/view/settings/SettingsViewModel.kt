package com.daya.notification_prototype.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.domain.settings.GetTopicAndSubscribedStatusUseCase
import com.daya.notification_prototype.domain.settings.SubscribeTopicUseCase
import com.daya.notification_prototype.domain.settings.UnSubScribeTopicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val getTopicAndSubscribedStatus: GetTopicAndSubscribedStatusUseCase,
    private val subscribeTopicUseCase: SubscribeTopicUseCase,
    private val unSubScribeTopicUseCase: UnSubScribeTopicUseCase
) : ViewModel(){

    private val _getTopicWithSubscribeStatus = liveData {
        emit(Resource.loading())
        val topics = getTopicAndSubscribedStatus(Unit)
        emit(topics)
    }

    val getTopicWithSubscribedStatusLiveData get() = _getTopicWithSubscribeStatus


     fun subscribeTopic(topic: Topic) {
        viewModelScope.launch {
             when (val isSubscribeSuccess = subscribeTopicUseCase(topic)) {
                is Resource.Success -> isSubscribeSuccess.data
            }
        }
    }

     fun unsubscribeTopic(topic: Topic) {
        viewModelScope.launch {
            when (val isSubscribeSuccess = unSubScribeTopicUseCase(topic)) {
                is Resource.Success -> isSubscribeSuccess.data
            }
        }
    }
}