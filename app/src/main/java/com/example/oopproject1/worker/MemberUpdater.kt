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

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * WorkManager for updating parliament members
 */
class MemberUpdater(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    private val repository: MemberRepository
    private val voteRepository: VoteRepository

    init {
        val memberDao = MemberDataBase.getDatabase(appContext).memberDao()
        repository = MemberRepository(memberDao)
        val voteDao = MemberDataBase.getDatabase(appContext).voteDao()
        val commentDao = MemberDataBase.getDatabase(appContext).commentDao()
        voteRepository = VoteRepository(voteDao, commentDao)
    }

    override suspend fun doWork(): Result {
        try {
            val members =
                ParliamentApi.retrofitService.getParliamentMembers()
            repository.clearMembers()       //delete old members
            members.forEach {
                repository.addMember(it)                    //add members to member_table
                val member = Vote(0,it.hetekaId)
                voteRepository.addVotableMember(member)     //add members to vote_table
            }

        } catch (e: Exception) {
            Log.d("MemberUpdateErr", e.toString())
            return Result.retry()
        }
        return Result.success()
    }
}