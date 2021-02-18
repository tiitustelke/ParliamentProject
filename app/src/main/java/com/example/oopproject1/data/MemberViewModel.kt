package com.example.oopproject1.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberViewModel(application: Application): AndroidViewModel(application) {

    private val getAllMembers: LiveData<List<ParliamentMember>>
    private val repository: MemberRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        getAllMembers = repository.getParliamentMembers
    }

    fun addMember(member: ParliamentMember) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMember(member)
        }
    }

}