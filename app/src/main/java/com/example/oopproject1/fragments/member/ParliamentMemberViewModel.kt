package com.example.oopproject1.fragments.member

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.oopproject1.data.*
import com.example.oopproject1.image.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Viewmodel for ParliamentMemberViewModel. Get data from ImageRepository and VoteRepository.
 * @see ImageRepository
 * @see VoteRepository
 */
class ParliamentMemberViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ImageRepository
    private val voteRepository: VoteRepository

    init {
        repository = ImageRepository(application)
        val voteDao = MemberDataBase.getDatabase(application).voteDao()
        val commentDao = MemberDataBase.getDatabase(application).commentDao()
        voteRepository = VoteRepository(voteDao, commentDao)
    }

    suspend fun getImage(member: ParliamentMember): Bitmap? = repository.getImage(member)

    suspend fun getVotes(hetekaId: Int): Int = viewModelScope.async(Dispatchers.IO) { voteRepository.getVotes(hetekaId) }.await()

    fun getComments(hetekaId: Int): LiveData<List<Comment>> = voteRepository.getComments(hetekaId)

    fun addComment(comment: Comment) = viewModelScope.launch(Dispatchers.IO) { voteRepository.addComment(comment) }

    fun minusVote(hetekaId: Int) = viewModelScope.launch(Dispatchers.IO) { voteRepository.minusVote(hetekaId) }

    fun plusVote(hetekaId: Int) = viewModelScope.launch(Dispatchers.IO) { voteRepository.plusVote(hetekaId) }
}