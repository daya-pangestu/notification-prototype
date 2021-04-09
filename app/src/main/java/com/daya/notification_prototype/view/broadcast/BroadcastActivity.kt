package com.daya.notification_prototype.view.broadcast

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.Resource
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.databinding.ActivityBroadcastBinding
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.model.Image
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BroadcastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastBinding
    private val viewModel by viewModels<BroadcastViewModel>()

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


            viewModel.getTopic().observe(this){ topics ->
                topics.forEach { topic ->
                    binding.chipGroupTopic.addView(buildChip(topic.topicName,topic.topicId))
                }
            }

            binding.btnDelImg.setOnClickListener {
                binding.imgChosenPic.setImageBitmap(null)
                isImgChosen(false)
            }

            binding.btnBroadcast.setOnClickListener {
                val titleText = binding.edTitle.text.toString()
                val descText = binding.edDesc.text.toString()
                val urlAccess = binding.edUrlAccess.text.toString()
                val uriLocalImage = binding.txtImgName.text.toString().removePrefix("name:").trim()

                val chosenTopics = binding.chipGroupTopic.children
                    .toList()
                    .filter {
                        (it as Chip).isChecked
                    }
                    .map {
                        val stringTopic = (it as Chip).text.toString().replace(" ", "_")
                        Topic(
                                topicName = stringTopic,
                                topicId = it.tag as String
                        )
                    }

                if (chosenTopics.isEmpty()){
                    Toast.makeText(it.context, "atleast 1 chip must be checked", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (titleText.isEmpty()){
                    Toast.makeText(it.context, "title mustn't empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (descText.isEmpty()){
                    Toast.makeText(it.context, "desc mustn't empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (urlAccess.isEmpty()){
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
                        topics = chosenTopics,
                        urlImage = uriLocalImage
                    )
                )
            }

            viewModel.broadcastinfoLiveData.observe(this){
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
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image: Image? = ImagePicker.getFirstImageOrNull(data)
            if (image != null) {
                binding.txtImgName.text = getString(R.string.img_name, image.path)

                Glide.with(this)
                        .load(image.path)
                        .into(binding.imgChosenPic)
                isImgChosen(true)
                return
            }
            // no need else branch, it returned, code below wont be executed when image not null
            isImgChosen(false)
        }

        super.onActivityResult(requestCode, resultCode, data)
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

    private fun isImgChosen(choosen: Boolean) {
        if (choosen) {
            binding.txtImgRatio.visibility = View.GONE
            binding.btnBrowseImg.visibility = View.GONE

            binding.imgChosenPic.visibility = View.VISIBLE
            binding.btnDelImg.visibility = View.VISIBLE
        } else {
            binding.txtImgRatio.visibility = View.VISIBLE
            binding.btnBrowseImg.visibility = View.VISIBLE

            binding.imgChosenPic.visibility = View.GONE
            binding.btnDelImg.visibility = View.GONE
        }
    }

}