package com.abdulgafur.demirci.secretdiaries.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.abdulgafur.demirci.secretdiaries.model.Note
import com.abdulgafur.demirci.secretdiaries.roomdb.NoteDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class NoteVM(app: Application) : AndroidViewModel(app) {

    val notes = mutableStateOf<List<Note>>(listOf())
    val note = mutableStateOf(Note("","", Date().toString()))

    private val database = Room.databaseBuilder(
        getApplication(),
        NoteDb::class.java,
        "notes.db"
    ).build()

    private val noteDao = database.noteDao()

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notes.value = noteDao.getNoteWithTitleAndId()
        }
    }

    fun getNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = noteDao.getNoteById(id)
            note?.let { selectedNote ->
                this@NoteVM.note.value = selectedNote
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(note)
        }
    }

}