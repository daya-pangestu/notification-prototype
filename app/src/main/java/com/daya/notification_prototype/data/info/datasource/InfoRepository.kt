package com.daya.notification_prototype.data.info.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.info.InfoEntity
import com.daya.notification_prototype.data.info.InfoNet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InfoRepository
@Inject
constructor(
    private val infoPagingSource: InfoPagingSource
) {

    fun infoPagingSource(): Flow<PagingData<InfoNet>> {
        return Pager(
            PagingConfig(pageSize =20)
        ){
            infoPagingSource
        }.flow
    }
}