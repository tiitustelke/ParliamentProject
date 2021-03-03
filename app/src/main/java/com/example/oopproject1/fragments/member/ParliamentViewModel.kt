package com.example.oopproject1.fragments.member

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.MemberRepository
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.image.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ParliamentViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ImageRepository

    init {
        repository = ImageRepository(application)
    }

    suspend fun getImage(member: ParliamentMember): Bitmap? {
        var bitmap: Bitmap? = null
        val fileName = member.lastname + "_" + member.firstname + ".jpg"

        if (repository.checkFileExists(fileName)) {
            bitmap = repository.loadFromCache(fileName)
        }
        else {
            viewModelScope.async(Dispatchers.IO) { bitmap = repository.fetchImage(member.pictureUrl) }.await()
            bitmap?.let { repository.cacheImage(fileName, it) }
        }
        return bitmap
    }
}