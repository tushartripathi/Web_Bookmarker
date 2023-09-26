package com.example.webbookmarker.Room.Notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NotesTable")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long =0,
    var notes: String? = null,
    var yAxis: Int ,
    var url: String?
)