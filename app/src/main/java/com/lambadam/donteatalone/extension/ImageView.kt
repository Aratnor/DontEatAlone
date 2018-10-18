package com.lambadam.donteatalone.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadFromUrl(context: Context, url: String){
    Glide.with(context).load(url).apply(RequestOptions.centerCropTransform()).into(this)
}