package com.example.oopproject1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addParliamentMember(member: ParliamentMember)

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //fun addParliamentMembers(members: ParliamentMembers)

    @Query("SELECT * FROM member_table ORDER BY lastname ASC")
    fun getParliamentMembers() : LiveData<List<ParliamentMember>>
}