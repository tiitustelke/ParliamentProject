package com.example.oopproject1.image

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import android.view.View
import android.widget.ImageView
import com.example.oopproject1.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.AccessController.getContext

/**
 * https://stackoverflow.com/a/17674787
 */
class ImageCache(appContext: Context) {
    private val context: Context = appContext
    val cw = ContextWrapper(context)
    // path to /data/data/yourapp/app_data/imageDir
    val directory = cw.getDir("imageCache", Context.MODE_PRIVATE)

    fun cacheImage(name: String,bitmap: Bitmap): String {

        // Create image directory
        val path = File(directory,name)

        var fos: FileOutputStream?  = null

        try {
            fos = FileOutputStream(path)
            // compress BitMap object for OutPutStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
        catch (e: Error) {
            Log.d("ImageCompressErr",e.toString())
        }
        finally {
            try {
                fos?.close()
            }
            catch (e: Error) {
                Log.d("ImageCacheErr",e.toString())
            }
        }
        return directory.absolutePath
    }

    fun loadFromCache(name: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val path = File(directory, name)

            bitmap = BitmapFactory.decodeStream(FileInputStream(path))
        }
        catch (e: Error) {
            Log.d("FileLoadErr",e.toString())
        }
        return bitmap
    }

    fun checkFileExists(name: String): Boolean {
        val file = File(directory, name)

        return file.exists()
    }

    fun fetchImage(url: String): Bitmap? {
        val ife = ImageFetcher()
        return ife.fetchImage(url)
    }
}