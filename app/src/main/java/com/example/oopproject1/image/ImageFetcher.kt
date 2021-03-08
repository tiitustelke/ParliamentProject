package com.example.oopproject1.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.InputStream
import java.net.URL

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Class for fetching photos of parliamentmembers from Finnish parliament's server
 */
private const val BASE_URL: String =  "https://avoindata.eduskunta.fi/"
class ImageFetcher {

    fun fetchImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url: URL = URL(BASE_URL + url)
            val connection = url.openConnection()

            val inputStream: InputStream = connection.getInputStream()
            bitmap = BitmapFactory.decodeStream(inputStream)

        } catch (e: Error) {
            Log.d("ImageFetchErr",e.toString())
        }
        return bitmap
    }
}