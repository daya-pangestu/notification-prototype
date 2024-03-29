package com.daya.notification_prototype.view.broadcast

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.databinding.ActivityBroadcastBinding
import com.daya.notification_prototype.util.toast
import com.daya.notification_prototype.view.broadcast.adapter.TopicAdapter
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.model.Image
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BroadcastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastBinding
    private val viewModel by viewModels<BroadcastViewModel>()

    @Inject
    lateinit var flexboxLayoutManager: FlexboxLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Broadcast Info"
            setDisplayHomeAsUpEnabled(true)
        }
        bindProgressButton(binding.btnBroadcast)
        binding.btnBroadcast.attachTextChangeAnimator()

        isImgChosen(false)
        binding.btnBrowseImg.setOnClickListener {
            ImagePicker.create(this@BroadcastActivity)
                    .returnMode(ReturnMode.ALL)
                    .single()
                    .folderMode(true)
                    .includeVideo(false)
                    .start()
        }

        val topicAdapter = TopicAdapter{ topic: Topic, _: CompoundButton, isCheked: Boolean ->
            if (isCheked) {
                viewModel.addTopic(topic)
            } else {
                viewModel.removeTopic(topic)
            }
        }

        binding.rvBroadcastTopic.apply {
            adapter = topicAdapter
            layoutManager = flexboxLayoutManager
        }

        viewModel.getTopic().observe(this) { topics ->
            when (topics) {
                is Resource.Loading ->{
                    setbtnBroadCastEnabled(false)
                }
                is Resource.Success -> {
                    topicAdapter.submitList(topics.data)
                    setbtnBroadCastEnabled(true)
                }
                is Resource.Error -> {
                    toast("error for getting topic to subscribe")
                    setbtnBroadCastEnabled(false)
                }
            }
        }

        binding.btnDelImg.setOnClickListener {

            viewModel.deleteUriImage()
        }

        binding.btnBroadcast.setOnClickListener {
            val titleText = binding.edTitle.text.toString()
            val descText = binding.edDesc.text.toString()
            val urlAccess = binding.edUrlAccess.text.toString()
            val uriLocalImage = viewModel.getUriImage().value ?: ""

            val chosenTopics = viewModel.getChoosenTopic()

            if (chosenTopics.isEmpty()) {
                Toast.makeText(it.context, "atleast 1 chip must be checked", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (titleText.isEmpty()) {
                Toast.makeText(it.context, "title mustn't empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (descText.isEmpty()) {
                Toast.makeText(it.context, "desc mustn't empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (urlAccess.isEmpty()) {
                Toast.makeText(it.context, "url access mustn't empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.btnBroadcast.showProgress {
                buttonText = "broadcasting"
                progressColor = Color.WHITE
            }

            viewModel.broadCastInfo(
                    Info(
                            title = titleText,
                            description = descText,
                            urlAccess = urlAccess,
                            topics = chosenTopics.toList(),
                            urlImage = uriLocalImage
                    )
            )
        }

        viewModel.broadcastingLiveData.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    val progress = it.progress
                    binding.btnBroadcast.showProgress {
                        buttonText = "$progress"
                        progressColor = Color.WHITE
                    }
                }
                is Resource.Success -> {
                    binding.btnBroadcast.hideProgress("info submitted")
                }
                is Resource.Error -> {
                    binding.btnBroadcast.hideProgress("failed, retry?")
                    Toast.makeText(this@BroadcastActivity, "${it.exceptionMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.getUriImage().observe(this){
            Glide.with(this)
                    .load(it)
                    .into(binding.imgChosenPic)

            isImgChosen(it.isNotEmpty())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image: Image? = ImagePicker.getFirstImageOrNull(data)
            if (image != null) {
                binding.txtImgName.text = getString(R.string.img_name, image.path)
                viewModel.setUriImage(image.path)
                return
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setbtnBroadCastEnabled(isEnabled: Boolean) {
        binding.btnBroadcast.isEnabled = isEnabled
    }

    private fun buildChip(text: String, id : String): Chip {
        return  Chip(ContextThemeWrapper(this@BroadcastActivity,R.style.Widget_MaterialComponents_Chip_Choice)).apply {
            this.text = text
            tag = id
            isCloseIconVisible = false
            isCheckable = true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    private fun isImgChosen(showImage: Boolean) {
        with(binding) {
            txtImgRatio.isVisible = !showImage
            btnBrowseImg.isVisible = !showImage

            imgChosenPic.isVisible = showImage
            btnDelImg.isVisible = showImage
        }
    }

}