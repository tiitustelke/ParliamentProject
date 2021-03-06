package com.example.oopproject1.fragments.member

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.ParliamentMember
import com.example.oopproject1.data.VoteRepository
import com.example.oopproject1.image.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ParliamentMemberViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ImageRepository
    private val voteRepository: VoteRepository

    init {
        repository = ImageRepository(application)
        val voteDao = MemberDataBase.getDatabase(application).voteDao()
        voteRepository = VoteRepository(voteDao)
    }

    suspend fun getImage(member: ParliamentMember): Bitmap? = repository.getImage(member)

    suspend fun getVotes(hetekaId: Int): Int = viewModelScope.async(Dispatchers.IO) { voteRepository.getVotes(hetekaId) }.await()

    fun minusVote(hetekaId: Int) = viewModelScope.launch(Dispatchers.IO) { voteRepository.minusVote(hetekaId) }

    fun plusVote(hetekaId: Int) = viewModelScope.launch(Dispatchers.IO) { voteRepository.plusVote(hetekaId) }
}