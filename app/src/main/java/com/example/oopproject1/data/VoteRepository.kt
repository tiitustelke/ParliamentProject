package com.example.oopproject1.data

class VoteRepository(private val voteDao: VoteDao, private val commentDao: CommentDao) {

    fun plusVote(hetakaId: Int) = voteDao.plusVote(hetakaId)

    fun minusVote(hetakaId: Int) = voteDao.minusVote(hetakaId)

    fun addVotableMember(member: Vote) = voteDao.addVotableMember(member)

    fun getVotes(hetakaId: Int) = voteDao.getVotes(hetakaId)

    fun getComments(hetakaId: Int) = commentDao.getComments(hetakaId)

    fun addComment(comment: Comment) = commentDao.addComment(comment)
}