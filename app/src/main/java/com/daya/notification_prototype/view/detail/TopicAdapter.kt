package com.daya.notification_prototype.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daya.notification_prototype.data.topic.Topic
import com.daya.notification_prototype.databinding.ItemTopicBinding

class TopicAdapter : ListAdapter<Topic, TopicAdapter.TopicViewHolder>(topicDIffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TopicViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Topic) {
            binding.chipTopic.apply {
                text = item.topicName
                isCheckable = false
            }
        }
    }

    companion object {
        private val topicDIffUtil = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem.topicId == newItem.topicId
            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem == newItem
        }
    }
}