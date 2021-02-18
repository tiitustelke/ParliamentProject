/*package com.example.oopproject1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "ParliamentDB"
val TABLE_NAME = "ParliamentMember"
val COL_HETEKAID = "hetekaId"
val COL_SEATNUMBER = "seatNumber"
val COL_LASTNAME = "lastName"
val COL_FIRSTNAME = "firstName"
val COL_PARTY = "party"
val COL_MINISTER = "minister"
val COL_PICTUREURL = "pictureUrl"

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + " (" +
                COL_HETEKAID + " INTEGER PRIMARY KEY," +
                COL_SEATNUMBER + " INTEGER," +
                COL_LASTNAME + " VARCHAR(256)," +
                COL_FIRSTNAME + " VARCHAR(256)," +
                COL_PARTY + " VARCHAR(256)," +
                COL_MINISTER + " BOOLEAN CHECK ($COL_MINISTER IN (0,1))," +
                COL_PICTUREURL + " VARCHAR(256));")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addParliamentMember(member : ParliamentMember): Long {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_HETEKAID,member.hetekaId)
        cv.put(COL_SEATNUMBER,member.seatNumber)
        cv.put(COL_LASTNAME,member.lastname)
        cv.put(COL_FIRSTNAME,member.firstname)
        cv.put(COL_PARTY,member.party)
        cv.put(COL_MINISTER,member.minister)
        cv.put(COL_PICTUREURL,member.pictureUrl)
        val result = db.insert(TABLE_NAME,null,cv)

        db.close()
        return result
    }

    fun getParliamentMembers(): MutableList<ParliamentMember> {
        var list : MutableList<ParliamentMember> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()) {
            do {
                val minister = false
                val member = ParliamentMember(
                    result.getInt(result.getColumnIndex(COL_HETEKAID)),
                    result.getInt(result.getColumnIndex(COL_SEATNUMBER)),
                    result.getString(result.getColumnIndex(COL_LASTNAME)),
                    result.getString(result.getColumnIndex(COL_FIRSTNAME)),
                    result.getString(result.getColumnIndex(COL_PARTY)),
                    minister,
                    result.getString(result.getColumnIndex(COL_PICTUREURL))

                )
                list.add(member)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

}*/