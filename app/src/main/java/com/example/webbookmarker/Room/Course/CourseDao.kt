package com.example.webbookmarker.Room.Course

import androidx.room.Insert

@androidx.room.Dao
interface CourseDao {
    @Insert
    fun insert(note: CourseModal?)
}