package com.example.webbookmarker.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.webbookmarker.Room.Notes.NoteDao
import com.example.webbookmarker.Room.Notes.NotesEntity


@Database(entities = [NotesEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao?


}
