package com.daya.notification_prototype.domain

import com.daya.notification_prototype.data.settings.SettingRepository
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetTopicAndSubscribedStatus
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