package com.ngedev.postcat.utils.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageBinder {
    fun bind(context: Context,imageSource: String, view: ImageView) {
        Glide.with(context).load(imageSource).into(view)
    }
}