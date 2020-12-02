/*
 * ImageBindingAdapter.kt 2020. 11. 15
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * @author MyeongKi
 */

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "glideOptions", "errorAttrResId", "completeLoadEvent"], requireAll = false)
    fun setImageUrl(
        imageView: ImageView,
        imageUrl: String,
        options: RequestOptions?,
        @AttrRes errorAttrResId: Int?,
        completeLoadEvent: () -> Unit
    ) {
        val glideRequest = Glide.with(imageView.context).load(imageUrl)
        with(glideRequest) {
            options?.let {
                apply(it)
            }
            errorAttrResId?.let {
                error(it)
                imageView.setBackgroundResource(it)
            }
            listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    completeLoadEvent.invoke()
                    return false
                }

            })
            glideRequest.into(imageView)
        }
    }
}