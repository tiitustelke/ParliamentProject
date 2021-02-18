package com.example.oopproject1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//data class ParliamentMembers (val records:List<ParliamentMember>) {}
@Entity(tableName = "member_table")
data class ParliamentMember(
    @PrimaryKey
    val hetekaId: Int,
    val seatNumber: Int = 0,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean = false,
    val pictureUrl: String = ""
)