package com.daya.notification_prototype.view.broadcast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BroadcastViewModel
@ViewModelInject
constructor(

) :ViewModel() {

    val topicLivedata: MutableLiveData<List<String>> = MutableLiveData()

    init {
        setTopic()
    }

    private fun setTopic() {
        topicLivedata.value = arrayListOf(
            "ITTP",
            "dosen",
            "lab"
        )
    }


    //TODO getTopic from firestore
    fun getTopic(): MutableLiveData<List<String>> {
        return topicLivedata
    }

}

