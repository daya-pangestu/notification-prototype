package com.daya.notification_prototype.view.broadcast

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.daya.notification_prototype.R
import com.daya.notification_prototype.databinding.ActivityBroadcastBinding
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.model.Image
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import timber.log.Timber


class BroadcastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isImgChoosen(false)
            binding.btnBrowseImg.setOnClickListener {
                ImagePicker.create(this@BroadcastActivity)
                        .returnMode(ReturnMode.ALL)
                        .single()
                        .folderMode(true)
                        .includeVideo(false)
                        .start()
            }

        binding.edTitle.setOnClickListener{
            binding.chipGroupTopic.children
                    .toList()
                    .map { it as Chip }
                    .filter { it.isChecked }
                    .forEach {
                        Timber.d("chip name ${it.text}")
                    }
        }

        binding.btnDelImg.setOnClickListener {
            binding.imgChosenPic.setImageBitmap(null)
            isImgChoosen(false)
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


    private fun buildChip(text: String, chipId:String ): Chip {
        return  Chip(ContextThemeWrapper(this@BroadcastActivity,R.style.Widget_MaterialComponents_Chip_Choice)).apply {
            this.text = text
            setChipBackgroundColorResource(R.color.ef_colorAccent)
            isCloseIconVisible = true
            setTextColor(resources.getColor(R.color.white))
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