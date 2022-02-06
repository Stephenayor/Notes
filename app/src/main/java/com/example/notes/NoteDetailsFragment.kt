package com.example.notes

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteDetailsBinding
import com.example.notes.databinding.FragmentNoteListBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_note_details.*

class NoteDetailsFragment : Fragment() {
    private lateinit var noteTitle: String
    private lateinit var noteBody: String
    private lateinit var binding: FragmentNoteDetailsBinding
    private lateinit var notesDatabase: NotesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentNoteDetailsBinding>(inflater,
                R.layout.fragment_note_details, container,
                false)
        notesDatabase = NotesDatabase.getInstance(context!!)
        initViews()


        return binding.root
    }

    private fun initViews() {
        binding.apply {
            noteTitle = noteTitleEditText.text.toString()
            noteBody = noteBodyEditText.text.toString()
            binding.saveNotesButton.setOnClickListener { view: View ->
                saveNotes()
            }
        }

    }

    private fun saveNotes() {
        noteTitle = binding.noteTitleEditText.text.toString()
        noteBody = binding.noteBodyEditText.text.toString()
        var notes = Notes(null, noteTitle, noteBody)
        notesDatabase.notesDao.saveNote(notes)
        Toast.makeText(context, "Your Note has been saved to the Database. ", Toast.LENGTH_SHORT).show()

    }

}