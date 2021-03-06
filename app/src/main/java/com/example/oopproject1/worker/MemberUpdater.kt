package com.example.oopproject1.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.oopproject1.API.ParliamentApi
import com.example.oopproject1.data.MemberDataBase
import com.example.oopproject1.data.MemberRepository
import com.example.oopproject1.data.Vote
import com.example.oopproject1.data.VoteRepository

class MemberUpdater(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    private val repository: MemberRepository
    private val voteRepository: VoteRepository
    init {
        val memberDao = MemberDataBase.getDatabase(appContext).memberDao()
        repository = MemberRepository(memberDao)
        val voteDao = MemberDataBase.getDatabase(appContext).voteDao()
        voteRepository = VoteRepository(voteDao)
    }
    override suspend fun doWork(): Result {
        try {
            val members =
                ParliamentApi.retrofitService.getParliamentMembers()
            repository.clearMembers()
            members.forEach {
                repository.addMember(it)
                val member = Vote(0,it.hetekaId)
                voteRepository.addVotableMember(member)
            }

        } catch (e: Exception) {
            Log.d("MemberUpdateErr", e.toString())
            return Result.failure()
        }
        return Result.success()
    }
}