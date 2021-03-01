package com.example.oopproject1.data

import androidx.lifecycle.LiveData

class MemberRepository(private val memberDao: MemberDao) {

    suspend fun addMember(member: ParliamentMember) {
        memberDao.addParliamentMember(member)
    }
    fun getMembers() = memberDao.getParliamentMembers()

    fun clearMembers() = memberDao.deleteMembers()

    fun getMember(pos: Int) = memberDao.getMember(pos)

    fun getParties() = memberDao.getParties()
}