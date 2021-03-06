package com.example.oopproject1.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//data class ParliamentMembers (val records:List<ParliamentMember>) {}
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