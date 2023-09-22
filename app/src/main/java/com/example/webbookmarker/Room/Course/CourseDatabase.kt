package com.example.webbookmarker.Room.Course

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CourseModal::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao


}
