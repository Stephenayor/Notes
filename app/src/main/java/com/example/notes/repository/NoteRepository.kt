package com.example.notes.repository

import androidx.lifecycle.LiveData
import com.example.notes.Notes
import com.example.notes.database.NotesDao

class NoteRepository(private val notesDao: NotesDao){

    val notesList: LiveData<List<Notes>> = notesDao.getAllNotes()

    suspend fun addNotes(note: Notes) {
        notesDao.saveNote(note)
    }
}