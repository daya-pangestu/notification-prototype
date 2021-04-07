package com.daya.notification_prototype.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.datasource.InfoRepository
import com.daya.notification_prototype.util.mapToPojo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InfoPagingUseCase
@Inject constructor(
    private val repo : InfoRepository,
)  {

    operator fun invoke(): Flow<PagingData<Info>> {
        return repo.infoPagingSource().map { pagingData ->
            pagingData.map { infoEntity ->
                infoEntity.mapToPojo()
            }
        }
    }
}