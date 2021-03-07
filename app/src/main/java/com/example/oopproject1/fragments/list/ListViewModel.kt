package com.example.oopproject1.fragments.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.MemberRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class ListViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MemberRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
    }

    fun getMembers() =  repository.getMembers()

    fun getMember(pos: Int) = viewModelScope.async(Dispatchers.IO) { repository.getMember(pos) }

}