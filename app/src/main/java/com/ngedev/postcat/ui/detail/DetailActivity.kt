package com.ngedev.postcat.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ngedev.postcat.databinding.ActivityDetailBinding
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.utils.helper.DateConverter.setLocalDateFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val story = intent.getParcelableExtra<Story>(STORY_PARCEL)
        parseStoryData(story)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun parseStoryData(story: Story?) {
        if (story != null) {
            binding.apply {
                tvStoryUsername.text = story.name
                tvStoryDescription.text = story.description
                tvStoryDate.text = setLocalDateFormat(story.createdAt)

                Glide
                    .with(this@DetailActivity)
                    .load(story.photoUrl)
                    .into(ivStoryImage)
            }
        }
    }

    companion object {
        const val STORY_PARCEL = "story_parcel"
    }
}