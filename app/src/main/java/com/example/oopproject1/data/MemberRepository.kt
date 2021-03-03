package com.example.oopproject1.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.oopproject1.worker.MemberUpdater
import java.util.concurrent.TimeUnit

class MemberRepository(private val memberDao: MemberDao) {

    fun addMember(member: ParliamentMember) {
        memberDao.addParliamentMember(member)
    }
    fun getMembers() = memberDao.getParliamentMembers()

    fun clearMembers() = memberDao.deleteMembers()

    fun getMember(pos: Int) = memberDao.getMember(pos)

    fun getParties() = memberDao.getParties()

    fun getMembersByParty(party: String) = memberDao.getMembersByParty(party)

    fun getMemberByParty(party: String, pos: Int) = memberDao.getMemberByParty(party, pos)

    fun updateMembers(context: Context) {
        val memberUpdater = PeriodicWorkRequestBuilder<MemberUpdater>(4, TimeUnit.HOURS)
                .build()
        WorkManager.getInstance(context).enqueue(memberUpdater)
    }
}