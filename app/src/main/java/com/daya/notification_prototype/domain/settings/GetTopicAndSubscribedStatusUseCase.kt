package com.daya.notification_prototype.domain.settings

import com.daya.notification_prototype.data.settings.datasource.SettingRepository
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.di.IoDispatcher
import com.daya.notification_prototype.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
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
            .map {
                val isCommon = it in topicWithSubscribedStatus
                Topic(topicId = it.topicId,topicName = it.topicName,isUserSubscribed =isCommon )
            }

        return commonTopic
    }

}