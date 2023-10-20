package com.ngedev.postcat.ui.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ngedev.postcat.databinding.StoryItemBinding
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.utils.helper.DateConverter
import com.ngedev.postcat.utils.helper.ImageBinder

class StoryAdapter: PagingDataAdapter<Story, StoryAdapter.StoryViewHolder>(DiffCallBack) {

    var onClickListener: ((Story, ActivityOptionsCompat) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemBinding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind( it) }
    }


    inner class StoryViewHolder(private val binding: StoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            with(binding) {
                tvStoryUsername.text = item.name
                tvStoryDate.text = DateConverter.setLocalDateFormat(item.createdAt)
                ImageBinder.bind(itemView.context, item.photoUrl, ivStoryImage)
                tvStoryDescription.text = item.description

                root.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            Pair(ivStoryImage, "story_image"),
                            Pair(tvStoryUsername, "username"),
                            Pair(tvStoryDate, "date"),
                            Pair(tvStoryDescription, "description")
                        )
                    onClickListener?.invoke(item,optionsCompat)
                }
            }
        }
    }

    companion object {
        val DiffCallBack = object: DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem.id == newItem.id

        }
    }
}