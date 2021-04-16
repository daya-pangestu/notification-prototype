package com.daya.notification_prototype.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.databinding.ActivityMainBinding
import com.daya.notification_prototype.util.toast
import com.daya.notification_prototype.view.broadcast.BroadcastActivity
import com.daya.notification_prototype.view.detail.DetailActivity
import com.daya.notification_prototype.view.detail.DetailActivity.Companion.DETAIL_INFO_EXTRA
import com.daya.notification_prototype.view.login.LoginActivity
import com.daya.notification_prototype.view.main.adapter.InfoPagingAdapter
import com.daya.notification_prototype.view.main.adapter.InfoPagingLoadingAdapter
import com.daya.notification_prototype.view.settings.SettingsActivity
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val viewModel :MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val infoPagingAdapter = InfoPagingAdapter{info : Info ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DETAIL_INFO_EXTRA,info)
            startActivity(intent)
        }

        infoPagingAdapter.addLoadStateListener { loadState: CombinedLoadStates ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.isVisible = true
            } else {
                binding.progressBar.isVisible = false

                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    toast(it.error.toString())
                }
            }
        }

        binding.rvMain.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = infoPagingAdapter.withLoadStateFooter(
                    footer = InfoPagingLoadingAdapter { infoPagingAdapter.retry() },
            )
        }

        lifecycleScope.launch {
            viewModel.infoPagingLiveData().collect {
                infoPagingAdapter.submitData(it)
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
            R.id.menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logout -> {
                //todo add confirmation to logout dialog
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}