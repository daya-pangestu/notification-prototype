package com.daya.notification_prototype.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.daya.notification_prototype.R
import com.daya.notification_prototype.databinding.ActivityMainBinding
import com.daya.notification_prototype.view.broadcast.BroadcastActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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