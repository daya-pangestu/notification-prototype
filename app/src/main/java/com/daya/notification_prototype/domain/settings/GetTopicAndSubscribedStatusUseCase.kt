package com.daya.notification_prototype.domain.settings

import com.daya.notification_prototype.data.settings.datasource.SettingRepository
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.di.IoDispatcher
import com.daya.notification_prototype.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber
import javax.inject.Inject

class GetTopicAndSubscribedStatusUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repo : SettingRepository
) : UseCase<Unit, List<Topic>>(coroutineDispatcher) {

    override suspend fun execute(parameters: Unit): List<Topic> {
        val defaultListTopic = repo.getAllTopic()
        val topicWithSubscribedStatus = repo.getSubScribedTopic()
        val commonTopic = defaultListTopic
            .map {topicNet ->
                val isCommon = topicNet.topicName in topicWithSubscribedStatus
                Topic(topicId = topicNet.topicId,topicName = topicNet.topicName,isUserSubscribe =isCommon,isUnsubscribeAble = topicNet.isUnsubscribeAble)
            }
        Timber.i("comonTopic $commonTopic")
        return commonTopic
    }
}