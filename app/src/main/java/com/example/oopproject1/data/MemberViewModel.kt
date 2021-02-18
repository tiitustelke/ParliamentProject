package com.example.oopproject1.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.oopproject1.API.ParliamentApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemberViewModel(application: Application): AndroidViewModel(application) {

    //private val _getAllMembers = MutableLiveData<List<ParliamentMember>>()
    private val getAllMembers: LiveData<List<ParliamentMember>>
    private val repository: MemberRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
        getAllMembers = repository.getParliamentMembers
    }

    fun addMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val members =
                    ParliamentApi.retrofitService.getParliamentMembers() //use here the covidApi to get the values
                members.forEach { repository.addMember(it) }
            } catch (e: Exception) {
                Log.d("***", e.toString())
            }
        }
    }

    fun getMembers() = repository.getMembers()

}