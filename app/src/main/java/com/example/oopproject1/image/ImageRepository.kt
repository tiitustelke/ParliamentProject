package com.example.oopproject1.image

import android.content.Context
import android.graphics.Bitmap

class ImageRepository(context: Context) {
    private val imageCache = ImageCache(context)

    fun cacheImage(name: String, bitmap: Bitmap) = imageCache.cacheImage(name, bitmap)

    fun loadFromCache(name: String) = imageCache.loadFromCache(name)

    fun fetchImage(url: String) = imageCache.fetchImage(url)

    fun checkFileExists(name: String) = imageCache.checkFileExists(name)
}