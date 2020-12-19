package com.daya.notification_prototype.view.broadcast

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.broadcast.Info
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
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class BroadcastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastBinding
    val viewmodel by viewModels<BroadcastViewModel>()

    @Inject lateinit var random : Random.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindProgressButton(binding.btnBroadcast)
        binding.btnBroadcast.attachTextChangeAnimator()

        isImgChoosen(false)
            binding.btnBrowseImg.setOnClickListener {
                ImagePicker.create(this@BroadcastActivity)
                        .returnMode(ReturnMode.ALL)
                        .single()
                        .folderMode(true)
                        .includeVideo(false)
                        .start()
            }


        viewmodel.topicLivedata.observe(this){ topics ->
            topics.forEach { topic ->
                binding.chipGroupTopic.addView(buildChip(topic.topicName,topic.topicId))
            }
        }

        binding.btnDelImg.setOnClickListener {
            binding.imgChosenPic.setImageBitmap(null)
            isImgChoosen(false)
        }

        binding.btnBroadcast.setOnClickListener {
            val titleText = binding.edTitle.text.toString()
            val descText = binding.edDesc.text.toString()
            val urlAccess = binding.edUrlAccess.text.toString()

            val choosenTopics = binding.chipGroupTopic.children
                .toList()
                .filter {
                    (it as Chip).isChecked
                }
                .map {
                    (it as Chip).text.toString().replace(" ","_")
                }

            if (choosenTopics.isEmpty()){
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

            viewmodel.broadCastInfo(
                Info(
                    title = titleText,
                    description = descText,
                    urlAccess = urlAccess,
                    topics = choosenTopics
                    )
                )

            binding.btnBroadcast.hideProgress("submited")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image: Image? = ImagePicker.getFirstImageOrNull(data)

            if (image != null) {
                binding.txtImgName.text = "image name: ${image.path}"
                Glide.with(this)
                        .load(image.path)
                        .into(binding.imgChosenPic)
                isImgChoosen(true)
                return
            }
            // no need else branch, it returned, code below wont be executed when image not null
            isImgChoosen(false)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun buildChip(text: kotlin.String, id : kotlin.String): Chip {
        return  Chip(ContextThemeWrapper(this@BroadcastActivity,R.style.Widget_MaterialComponents_Chip_Choice)).apply {
            this.text = text
            tag = id
            isCloseIconVisible = false
            isCheckable = true

        }
    }

    private fun isImgChoosen(choosen: Boolean) {
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

    companion object{
        private const val IMAGE_PICKER_REQUEST_CODE = 1091
    }
}