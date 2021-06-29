package com.daya.notification_prototype.domain.pref

import com.daya.notification_prototype.data.settings.datasource.SettingRepository
import javax.inject.Inject

class IsFirstTimeUseCase
@Inject
constructor(
        private val settingRepository: SettingRepository
) {

    operator fun invoke(): Boolean {
        return settingRepository.isFirstTime()
    }
}