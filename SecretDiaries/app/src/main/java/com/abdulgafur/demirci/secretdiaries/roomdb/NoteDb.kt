package com.abdulgafur.demirci.secretdiaries.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdulgafur.demirci.secretdiaries.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}