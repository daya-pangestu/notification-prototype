package com.daya.notification_prototype.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.broadcast.Topic
import com.daya.notification_prototype.databinding.ActivityMainBinding
import com.daya.notification_prototype.view.broadcast.BroadcastActivity
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var messaging :FirebaseMessaging

    val viewmodel :MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.topicLivedata.observe(this){ topics :List<Topic> ->
            topics.forEach { topic ->
               messaging.subscribeToTopic(topic.topicName).addOnCompleteListener { task ->
                   var msg = "subscring to ${topic.topicName} succes"
                   if (!task.isSuccessful) {
                       msg = "subscribing to ${topic.topicName} failed"
                   }
                   Timber.i(msg)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_broadcast -> {
                val intent = Intent(this, BroadcastActivity::class.java)
                startActivity(intent)
                true
            }

        else -> super.onOptionsItemSelected(item)
        }
    }
}