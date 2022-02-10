package com.example.notes.database

import android.icu.text.Replaceable
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.Notes

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