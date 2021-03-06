package com.example.oopproject1.data

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oopproject1.image.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PartyMemberViewModel(application: Application): AndroidViewModel(application) {

    private val memberRepo: MemberRepository
    private val imgRepo: ImageRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        memberRepo = MemberRepository(memberDao)
        imgRepo = ImageRepository(application)
    }

    fun updateMembers() = memberRepo.updateMembers(getApplication())

    fun getMembers() =  memberRepo.getMembers()

    fun getMembersByParty(party: String) = memberRepo.getMembersByParty(party)

    suspend fun getMember(pos: Int) = viewModelScope.async(Dispatchers.IO) { memberRepo.getMember(pos) }

    fun getMemberByParty(party: String, pos: Int) = viewModelScope.async(Dispatchers.IO) { memberRepo.getMemberByParty(party, pos) }

    fun deleteMembers() = viewModelScope.async(Dispatchers.IO) { memberRepo.clearMembers() }

    suspend fun getImage(member: ParliamentMember): Bitmap? = imgRepo.getImage(member)

}