package com.example.searchusers.ui.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.searchusers.R

@BindingAdapter("bindingUrl")
fun bindingUrl(iv: AppCompatImageView, url: String) {
    Glide.with(iv.context).load(url).placeholder(R.drawable.ic_baseline_image_24).circleCrop().into(iv)
}