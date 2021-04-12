package com.daya.notification_prototype.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.databinding.ActivityMainBinding
import com.daya.notification_prototype.view.broadcast.BroadcastActivity
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val viewModel :MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val infoPagingAdapter = InfoPagingAdapter{
            Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            viewModel.infoPagingLiveData().collect {
                infoPagingAdapter.submitData(it)
            }
        }

        binding.rvMain.apply {
            setHasFixedSize(true)
            adapter = infoPagingAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
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