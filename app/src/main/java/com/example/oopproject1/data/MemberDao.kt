package com.example.oopproject1.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addParliamentMember(member: ParliamentMember)

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //fun addParliamentMembers(members: ParliamentMembers)

    @Query("SELECT * FROM member_table ORDER BY lastname ASC LIMIT 1 OFFSET :offset")
    fun getMember(offset: Int) : ParliamentMember


    @Query("SELECT * FROM member_table ORDER BY lastname ASC")
    fun getParliamentMembers() : LiveData<List<ParliamentMember>>

    @Query("DELETE FROM member_table")
    fun deleteMembers()
}