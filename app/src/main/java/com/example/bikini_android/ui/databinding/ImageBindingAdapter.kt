/*
 * ImageBindingAdapter.kt 2020. 11. 15
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.databinding

import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author MyeongKi
 */

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "glideOptions", "errorAttrResId"], requireAll = false)
    fun setImageUrl(
        imageView: ImageView,
        imageUrl: String,
        options: RequestOptions?,
        @AttrRes errorAttrResId: Int?
    ) {
        val glideRequest = Glide.with(imageView.context).load(imageUrl)
        with(glideRequest){
            options?.let {
                apply(it)
            }
            errorAttrResId?.let {
                error(it)
                imageView.setBackgroundResource(it)
            }
        }
        glideRequest.into(imageView)
    }
}