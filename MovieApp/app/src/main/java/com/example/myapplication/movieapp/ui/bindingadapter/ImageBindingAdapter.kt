package com.example.myapplication.movieapp.ui.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("baseUrl", "path")
fun loadImage(view: ImageView, baseUrl: String, path: String?) {
    path?.apply {
        val imagePath = baseUrl + this
        Glide.with(view).load(imagePath).into(view)
    }
}