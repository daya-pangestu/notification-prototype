package com.daya.notification_prototype.view.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.databinding.ItemTopicBinding

class TopicAdapter (private val onCheckedChangeListener : (CompoundButton,Boolean) -> Unit): ListAdapter<Topic, TopicAdapter.TopicViewHolder>(topicDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding,)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onCheckedChangeListener)
    }

    inner class TopicViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Topic, onCheckedChangeListener: (CompoundButton, Boolean) -> Unit) {
            binding.chipTopic.apply {
                text = item.topicName
                isCheckable = item.isUnsubscribeAble
            }
            binding.chipTopic.setOnCheckedChangeListener(onCheckedChangeListener)
        }
    }

    companion object {
        private val topicDiffUtil = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem.topicId == newItem.topicId
            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean  = oldItem == newItem
        }
    }


}