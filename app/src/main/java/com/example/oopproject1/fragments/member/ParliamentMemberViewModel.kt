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

class ParliamentMemberViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ImageRepository

    init {
        repository = ImageRepository(application)
    }

    suspend fun getImage(member: ParliamentMember): Bitmap? = repository.getImage(member)
}