package com.example.oopproject1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Dao for storing votecounts for each member
 */
@Dao
interface VoteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addVotableMember(member: Vote)

    @Query("UPDATE vote_table SET votes = votes + 1 WHERE memberId = :hetekaId")
    fun plusVote(hetekaId: Int)

    @Query("UPDATE vote_table SET votes = votes - 1 WHERE memberId = :hetekaId")
    fun minusVote(hetekaId: Int)

    @Query("SELECT votes from vote_table WHERE memberId = :hetekaId")
    fun getVotes(hetekaId: Int) : Int
}