package com.daya.notification_prototype.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoNet
import com.daya.notification_prototype.data.info.datasource.InfoRepository
import com.daya.notification_prototype.data.topic.TopicNet
import com.daya.notification_prototype.util.mapper.TopicMapper.mapGeneralToNet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InfoPagingUseCase
@Inject constructor(
    private val repo : InfoRepository,
)  {

    operator fun invoke(): Flow<PagingData<Info>> {
        return repo.infoPagingSource().map {paging ->
            paging.map {
                Info(
                    senderId = it.senderId,
                    title = it.title,
                    urlAccess = it.urlAccess,
                    description = it.description,
                    urlImage = it.urlImage,
                    status = it.status,
                    topics = it.topics,
                    broadcastRequested = it.broadcastRequested
                )
            }
        }
    }
}