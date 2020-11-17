/*
 * BikiniGlideModule.kt 2020. 11. 15
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.bikini_android.util.logging.Logger
import java.lang.Exception

/**
 * @author MyeongKi
 */

@GlideModule
class BikiniGlideModule : AppGlideModule() {
    private val logger = Logger().apply {
        TAG = this@BikiniGlideModule.javaClass.simpleName
    }

    override fun isManifestParsingEnabled(): Boolean = false

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        try {
            val memorySizeCalculator = MemorySizeCalculator.Builder(context).build()
            val defaultMemoryCacheSize = convertToDefaultSizeByVersion(memorySizeCalculator.memoryCacheSize)
            val defaultBitmapPoolSize = convertToDefaultSizeByVersion(memorySizeCalculator.bitmapPoolSize)

            logger.info { "cache size: $defaultMemoryCacheSize" }
            logger.info { "bitmap pool size: $defaultMemoryCacheSize" }

            builder.apply {
                setDefaultRequestOptions(
                    RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).disallowHardwareConfig()
                )
                setMemoryCache(LruResourceCache(defaultMemoryCacheSize))
                setBitmapPool(LruBitmapPool(defaultBitmapPoolSize))
                setDiskCache { DiskLruCacheWrapper.create(Glide.getPhotoCacheDir(context), DISK_CACHE_MAX_SIZE) }
                setDefaultTransitionOptions(Drawable::class.java, DrawableTransitionOptions.withCrossFade())
                setDefaultTransitionOptions(Bitmap::class.java, BitmapTransitionOptions.withCrossFade())
            }

        } catch (e: Exception) {
            logger.error {
                e.toString()
            }
        }
    }

    private fun convertToDefaultSizeByVersion(size: Int): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (size / 1.5f).toLong()
        } else {
            (size / 3).toLong()
        }
    }

    companion object {
        private const val DISK_CACHE_MAX_SIZE = 1024 * 1024 * 100L
    }
}