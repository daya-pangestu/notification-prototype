package com.daya.notification_prototype.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daya.notification_prototype.R
import com.daya.notification_prototype.data.info.Info
import com.daya.notification_prototype.databinding.ItemInfoBinding

class InfoPagingAdapter(private val onItemClick : (Info) -> Unit) : PagingDataAdapter<Info,InfoPagingAdapter.InfoViewHolder>(infoComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item,onItemClick)
        } ///todo provide placeholder
    }


    inner class InfoViewHolder(private val binding :  ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Info, onItemClick: (Info) -> Unit) {
            Glide.with(itemView)
                .load(item.urlImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.imgInfo)

            binding.tvTitle.text = item.title
            binding.tvDesc.text = item.description

            itemView.setOnClickListener {
                onItemClick
            }
        }
    }

    companion object {
        private val infoComparator  = object : DiffUtil.ItemCallback<Info>() {
            override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean = oldItem.senderId == newItem.senderId
            override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean = oldItem == newItem

        }
    }
}