package com.example.oopproject1.image

import android.content.Context
import android.graphics.Bitmap

import com.example.oopproject1.data.ParliamentMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Repository for deciding if photos are loaded from the web or loaded from device memory
 */
class ImageRepository(context: Context) {
    private val imageCache = ImageCache(context)

    fun cacheImage(name: String, bitmap: Bitmap) = imageCache.cacheImage(name, bitmap)

    fun loadFromCache(name: String) = imageCache.loadFromCache(name)

    fun fetchImage(url: String) = imageCache.fetchImage(url)

    fun checkFileExists(name: String) = imageCache.checkFileExists(name)

    suspend fun getImage(member: ParliamentMember): Bitmap? {
        var bitmap: Bitmap? = null
        val fileName = member.lastname + "_" + member.firstname + ".jpg"

        //check if the photo of the parliamentmember is already downloaded to device
        if (checkFileExists(fileName)) {
            bitmap = loadFromCache(fileName)
        }
        else {
            GlobalScope.async(Dispatchers.IO) { bitmap = fetchImage(member.pictureUrl) }.await()
            bitmap?.let { cacheImage(fileName, it) }
        }
        return bitmap
    }
}