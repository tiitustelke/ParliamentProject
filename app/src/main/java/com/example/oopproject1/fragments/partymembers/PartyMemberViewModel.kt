package com.example.oopproject1.fragments.partymembers

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

class PartyMemberViewModel(application: Application): AndroidViewModel(application) {

    private val memberRepo: MemberRepository
    private val imgRepo: ImageRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        memberRepo = MemberRepository(memberDao)
        imgRepo = ImageRepository(application)
    }

    fun getMembersByParty(party: String) = memberRepo.getMembersByParty(party)

    fun getMemberByParty(party: String, pos: Int) = viewModelScope.async(Dispatchers.IO) { memberRepo.getMemberByParty(party, pos) }

    suspend fun getImage(member: ParliamentMember): Bitmap? = imgRepo.getImage(member)

}