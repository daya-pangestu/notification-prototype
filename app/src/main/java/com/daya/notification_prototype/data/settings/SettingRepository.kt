package com.daya.notification_prototype.data.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import com.daya.notification_prototype.data.topic.FirebaseTopicDataSource
import com.daya.notification_prototype.data.topic.TopicNet
import javax.inject.Inject

class SettingRepository
@Inject
constructor(
        private val pref: SharedPreferences?,
        private val topicDataSource: FirebaseTopicDataSource
) {
    fun isFirstTime(): Boolean {
        return pref?.getBoolean(IS_FIRST_TIME_KEY,true) ?: true
    }

    fun setFirstTime(firstTime: Boolean) {
        pref?.edit{
            putBoolean(IS_FIRST_TIME_KEY, firstTime)
        }
    }

    companion object {
        private const val IS_FIRST_TIME_KEY = "is_first_time_key"
    }

    suspend fun getAllTopic(): List<TopicNet> {
       return topicDataSource.getDefaultTopic()
    }

    suspend fun getSubScribedTopic(): List<TopicNet> {
        return emptyList()
    }
}