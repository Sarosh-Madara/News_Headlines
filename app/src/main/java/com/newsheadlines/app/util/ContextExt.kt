package com.newsheadlines.app.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun Context.loadImageFromURL(imageURL: String, imageView: ImageView) {
    Glide
        .with(this)
        .load(imageURL)
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(imageView)
}
