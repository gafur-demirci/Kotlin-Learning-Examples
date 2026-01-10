package com.abdulgafur.demirci.secretdiaries.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abdulgafur.demirci.secretdiaries.model.Note

@Dao
interface NoteDao {

    @Query("SELECT title, id FROM notes")
    fun getNoteWithTitleAndId(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Note?

    @Insert
    suspend fun insertNote(item: Note)

    @Delete
    suspend fun deleteNote(item: Note)
}