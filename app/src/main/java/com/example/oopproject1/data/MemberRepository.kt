package com.example.oopproject1.data

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.oopproject1.worker.MemberUpdater
import java.util.concurrent.TimeUnit

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Repository class for storing, fetching and updating Parliament members
 */

class MemberRepository(private val memberDao: MemberDao) {

    fun addMember(member: ParliamentMember) = memberDao.addParliamentMember(member)

    fun getMembers() = memberDao.getParliamentMembers()     //get all the parliament members

    fun clearMembers() = memberDao.deleteMembers()          //delete all the parliament members

    fun getMember(pos: Int) = memberDao.getMember(pos)      //get one member of position from the database

    fun getParties() = memberDao.getParties()               //list of all parties

    fun getMembersByParty(party: String) = memberDao.getMembersByParty(party)       //get all members of a party

    fun getMemberByParty(party: String, pos: Int) = memberDao.getMemberByParty(party, pos)      //get a member of a position in a party from the database

    fun updateMembers(context: Context) {           //update members in the database every 4 hours
        val memberUpdater = PeriodicWorkRequestBuilder<MemberUpdater>(4, TimeUnit.HOURS)
                .build()
        WorkManager.getInstance(context).enqueue(memberUpdater)
    }
}