package com.daya.notification_prototype.view.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import androidx.core.content.getSystemService
import com.bumptech.glide.Glide
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.databinding.ActivityDetailBinding
import com.daya.notification_prototype.util.toast
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    @Inject
    lateinit var flexboxLayoutManager: FlexboxLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "DETAIL"
            setDisplayHomeAsUpEnabled(true)
        }
        val info = intent.getParcelableExtra<Info>(DETAIL_INFO_EXTRA)

        val topicAdapter = TopicAdapter().apply {
            submitList(info?.topics)
        }

        with(binding) {
            Glide.with(this@DetailActivity)
                .load(info?.urlImage)
                .placeholder(R.drawable.placeholder)
                .into(imgBanner)

            tvUrlAccess.apply {
                text = info?.urlAccess
                movementMethod = ScrollingMovementMethod()
                setHorizontallyScrolling(true)
            }
                tvTitle.text = info?.title
                tvDesc.text = info?.description

                btnCopyLink.setOnClickListener {
                    val clipboard = getSystemService<ClipboardManager>()
                    val clipData = ClipData.newPlainText(null, info?.urlAccess)
                    clipboard?.setPrimaryClip(clipData)
                    toast("link copied")
                }
                btnShareLink.setOnClickListener {
                    val shareLink = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT,info?.urlAccess)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(shareLink, "share link")
                    startActivity(shareIntent)
                }

                rvTopic.apply {
                    layoutManager = flexboxLayoutManager
                    adapter = topicAdapter
                }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val DETAIL_INFO_EXTRA = "detail_info_extra"
    }
}