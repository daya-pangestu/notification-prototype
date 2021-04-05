package com.daya.notification_prototype.domain

import com.daya.notification_prototype.data.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<Resource<R>> = execute(parameters)
        .onStart { emit(Resource.loading()) }
        .catch { e -> emit(Resource.error(e.localizedMessage)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(param: P): Flow<Resource<R>>
}
