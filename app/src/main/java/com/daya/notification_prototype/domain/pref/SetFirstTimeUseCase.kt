package com.daya.notification_prototype.domain.pref

import com.daya.notification_prototype.data.settings.SettingRepository
import com.daya.notification_prototype.di.IoDispatcher
import com.daya.notification_prototype.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetFirstTimeUseCase
@Inject
constructor(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        private val settingRepository: SettingRepository
) : UseCase<Boolean, Unit>(coroutineDispatcher) {

    override suspend fun execute(parameters: Boolean) {
        settingRepository.setFirstTime(parameters)
    }
}