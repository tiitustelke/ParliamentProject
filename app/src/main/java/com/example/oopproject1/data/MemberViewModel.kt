package com.example.oopproject1.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.oopproject1.API.ParliamentApi
import com.example.oopproject1.worker.MemberUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class MemberViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MemberRepository

    init {
        val memberDao = MemberDataBase.getDatabase(application).memberDao()
        repository = MemberRepository(memberDao)
    }

    fun updateMembers() = repository.updateMembers(getApplication())

}