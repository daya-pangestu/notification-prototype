package com.daya.notification_prototype.domain

import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.broadcast.Info
import com.daya.notification_prototype.data.broadcast.datasource.BroadcastRepository
import com.daya.notification_prototype.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class BroadCastInfoUseCase
@Inject
constructor(
    private val repo : BroadcastRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Info, Void?>(coroutineDispatcher) {

    override fun execute(param: Info): Flow<Resource<Void?>> = flow {
        emit(Resource.loading())
        if (param.urlImage.isNullOrEmpty()) {
            emit(Resource.loading(progress = "broadcasting without Img"))
            val casted = repo.broadCastWithOutImg(param).let {
                Resource.success(it)
            }
            emit(casted)
        } else {
            emit(Resource.loading(progress = "broadcasting with Img"))
            val casted = repo.broadCastWithImg(param).let {
                Resource.success(it)
            }
            emit(casted)
        }
    }
}