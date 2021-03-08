package com.example.oopproject1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * Dao for storing and fetching comments with Room
 */
@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addComment(comment: Comment)

    @Query("SELECT * from comment_table WHERE memberId = :hetekaId")
    fun getComments(hetekaId: Int) : LiveData<List<Comment>>
}