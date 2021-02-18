package com.example.oopproject1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//https://www.youtube.com/watch?v=lwAvI3WDXBY
@Database(entities = [ParliamentMember::class], version = 1, exportSchema = false)
abstract class MemberDataBase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

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