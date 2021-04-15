package com.daya.notification_prototype.view.settings


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.databinding.ActivitySettingsBinding
import com.daya.notification_prototype.util.toast
import com.daya.notification_prototype.view.settings.adapter.TopicAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private val settingsViewModel by viewModels<SettingsViewModel>()
    private lateinit var binding : ActivitySettingsBinding

    @Inject
    lateinit var flexBoxLayoutManager: FlexboxLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val topicAdapter = TopicAdapter{ topic,compoundView, isChecked ->
            toast("${compoundView.text} $isChecked")
                if (isChecked) {
                     settingsViewModel.unsubscribeTopic(topic)
                } else {
                    settingsViewModel.subscribeTopic(topic)
                }
        }

        binding.rvTopic.apply {
            layoutManager = flexBoxLayoutManager
            adapter = topicAdapter
        }

        settingsViewModel.getTopicWithSubscribedStatusLiveData.observe(this){
            when (it) {
                is Resource.Loading -> {
                    setProgress(true)
                }
                is Resource.Success -> {
                    setProgress(false)
                    val topicList = it.data
                    topicAdapter.submitList(topicList)
                }
                is Resource.Error -> {
                    setProgress(false)
                    toast("error ${it.exceptionMessage}",Toast.LENGTH_LONG)
                }
            }
        }
    }

     private fun setProgress(isVisible : Boolean) {
         binding.progressBar.isVisible = isVisible
         binding.tvTopic.isVisible = !isVisible
         binding.rvTopic.isVisible = !isVisible
    }
}