package com.example.oopproject1.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This file is for all entities required for storing data
 */

@Parcelize
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
): Parcelable

@Entity(tableName = "vote_table",
        foreignKeys = [ForeignKey(entity = ParliamentMember::class,
            parentColumns = ["hetekaId"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.NO_ACTION
        )]
)
data class Vote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val memberId: Int,
    val votes: Int = 0
)

@Entity(tableName = "comment_table",
    foreignKeys = [ForeignKey(entity = ParliamentMember::class,
        parentColumns = ["hetekaId"],
        childColumns = ["memberId"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val memberId: Int,
    val userName: String,
    val comment: String,
    val time: String
)