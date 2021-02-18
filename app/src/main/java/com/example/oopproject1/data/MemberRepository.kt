package com.example.oopproject1.data

import androidx.lifecycle.LiveData

class MemberRepository(private val memberDao: MemberDao) {

    val getParliamentMembers: LiveData<List<ParliamentMember>> = memberDao.getParliamentMembers()

    suspend fun addMember(member: ParliamentMember) {
        memberDao.addParliamentMember(member)
    }
    //suspend fun addMembers(members: ParliamentMembers) {
        //memberDao.addParliamentMembers(members)
    //}
}