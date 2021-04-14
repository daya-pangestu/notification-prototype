package com.daya.notification_prototype.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.domain.settings.GetTopicAndSubscribedStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val getTopicAndSubscribedStatus: GetTopicAndSubscribedStatusUseCase
) : ViewModel(){

    private val _getTopicWithSubscribeStatus = liveData {
        emit(Resource.loading())
        val topics = getTopicAndSubscribedStatus(Unit)
        emit(topics)
    }

    val getTopicWithSubscribedStatusLiveData get() = _getTopicWithSubscribeStatus
}