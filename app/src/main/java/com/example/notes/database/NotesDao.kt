package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_list_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes_list_table WHERE id = :id")
    fun getNoteById(id: Int): Notes

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNote(note: Notes)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)
}