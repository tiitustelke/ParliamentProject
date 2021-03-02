package com.example.oopproject1.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.oopproject1.API.ParliamentApi
import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.MemberRepository

class MemberUpdater(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    private val repository: MemberRepository
    init {
        val memberDao = MemberDataBase.getDatabase(appContext).memberDao()
        repository = MemberRepository(memberDao)
    }
    override suspend fun doWork(): Result {
        try {
            val members =
                ParliamentApi.retrofitService.getParliamentMembers()
            repository.clearMembers()
            members.forEach { repository.addMember(it) }

        } catch (e: Exception) {
            Log.d("***", e.toString())
            return Result.failure()
        }
        return Result.success()
    }
}