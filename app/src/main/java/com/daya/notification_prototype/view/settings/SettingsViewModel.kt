package com.daya.notification_prototype.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.daya.notification_prototype.domain.settings.GetTopicAndSubscribedStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val getTopicAndSubscribedStatus: GetTopicAndSubscribedStatusUseCase
) : ViewModel(){

    val getTopicWithSubscribeStatus = liveData {
        val topics = getTopicAndSubscribedStatus(Unit)
        emit(topics)
    }
}