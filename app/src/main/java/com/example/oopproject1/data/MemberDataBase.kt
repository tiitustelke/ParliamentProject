package com.example.oopproject1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//https://www.youtube.com/watch?v=lwAvI3WDXBY
@Database(entities = [ParliamentMember::class, Vote::class, Comment::class], version = 6, exportSchema = false)
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
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}