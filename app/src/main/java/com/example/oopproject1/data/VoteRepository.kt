package com.example.oopproject1.data

class VoteRepository(private val voteDao: VoteDao) {

    fun plusVote(hetakaId: Int) = voteDao.plusVote(hetakaId)

    fun minusVote(hetakaId: Int) = voteDao.minusVote(hetakaId)

    fun addVotableMember(member: Vote) = voteDao.addVotableMember(member)

    fun getVotes(hetakaId: Int) = voteDao.getVotes(hetakaId)
}