package com.daya.notification_prototype.view.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.domain.main.InfoPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    infoPagingUseCase: InfoPagingUseCase
) : ViewModel() {

    private val _infoPagingFlow = infoPagingUseCase()
        .cachedIn(viewModelScope)

    fun infoPagingLiveData(): Flow<PagingData<Info>> {
        return _infoPagingFlow
    }

}
