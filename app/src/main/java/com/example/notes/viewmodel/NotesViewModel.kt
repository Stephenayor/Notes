package com.example.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.Notes
import com.example.notes.database.NotesDao
import com.example.notes.database.NotesDatabase
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(val notesDao: NotesDao,
                     application: Application)
    : AndroidViewModel(application) {

    val allSavedNotes : LiveData<List<Notes>>
    val repository : NoteRepository

        init {
            val notesDao = NotesDatabase.getInstance(application).notesDao
            repository = NoteRepository(notesDao)
            allSavedNotes = repository.notesList
        }


        fun deleteNote (notes: Notes) = viewModelScope.launch(Dispatchers.IO) {

        }

//        fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
//            repository.update(note)
//        }
//
        fun addNote(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
                repository.addNotes(notes)
            }


        }

