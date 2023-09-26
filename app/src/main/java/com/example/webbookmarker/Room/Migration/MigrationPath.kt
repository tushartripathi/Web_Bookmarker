package com.example.webbookmarker.Room.Migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationPath {

    val migration1to2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE NotesTable ADD COLUMN url TEXT")
        }
    }
}