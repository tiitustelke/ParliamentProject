package com.example.oopproject1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Class for Room database. I have used this tutorial for some of the database stuff: https://www.youtube.com/watch?v=lwAvI3WDXBY, Thank you Stevdza-San from Youtube
 */
@Database(entities = [ParliamentMember::class, Vote::class, Comment::class], version = 2, exportSchema = false)
abstract class MemberDataBase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun voteDao() : VoteDao

    abstract fun commentDao() : CommentDao

    companion object {
        @Volatile
        private var INSTANCE: MemberDataBase? = null

        fun getDatabase(context: Context): MemberDataBase {
            val tempInstance  = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemberDataBase::class.java,
                    "member_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}