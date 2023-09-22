package com.example.webbookmarker.Room.Course

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class CourseModal(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var courseName: String? = null,
    var courseDuration: String
)
