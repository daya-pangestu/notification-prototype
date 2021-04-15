package com.daya.notification_prototype.domain.broadcast

import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.data.topic.TopicRepository
import com.daya.notification_prototype.di.IoDispatcher
import com.daya.notification_prototype.domain.UseCase
import com.daya.notification_prototype.util.mapper.TopicMapper.mapNetToGeneral
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllTopicUseCase
@Inject
constructor(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        private val repo : TopicRepository
) : UseCase<Unit, List<Topic>>(coroutineDispatcher) {

    override suspend fun execute(parameters: Unit): List<Topic> {
        return repo.getDefaultTopic().mapNetToGeneral()
    }

}