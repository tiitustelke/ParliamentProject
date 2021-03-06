package com.example.oopproject1.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Dao for storing and fetching parliament members with Room
 */
@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addParliamentMember(member: ParliamentMember)

    @Query("SELECT * FROM member_table ORDER BY lastname ASC LIMIT 1 OFFSET :offset")
    fun getMember(offset: Int) : ParliamentMember

    @Query("SELECT * FROM member_table ORDER BY lastname ASC")
    fun getParliamentMembers() : LiveData<List<ParliamentMember>>

    @Query("DELETE FROM member_table")
    fun deleteMembers()

    @Query("SELECT DISTINCT party FROM member_table ORDER BY party ASC")
    fun getParties() : List<String>

    @Query("SELECT * FROM member_table WHERE party = :party ORDER BY lastname ASC")
    fun getMembersByParty(party: String) : LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM member_table WHERE party = :party ORDER BY lastname ASC LIMIT 1 OFFSET :offset")
    fun getMemberByParty(party: String, offset: Int) : ParliamentMember

}