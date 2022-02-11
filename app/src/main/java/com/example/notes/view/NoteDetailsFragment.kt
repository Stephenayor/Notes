package com.example.notes.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notes.NoteDetailsFragmentArgs
import com.example.notes.NoteDetailsFragmentDirections
import com.example.notes.R
import com.example.notes.database.Notes
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteDetailsBinding
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_details.*

class NoteDetailsFragment() : Fragment() {
    private lateinit var noteTitle: String
    private lateinit var noteBody: String
    private var noteID: Int = -1
    private lateinit var binding: FragmentNoteDetailsBinding
    private lateinit var notesDatabase: NotesDatabase
    lateinit var notesViewModel: NotesViewModel
    private lateinit var noteDetails: Notes


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNoteDetailsBinding>(
            inflater, R.layout.fragment_note_details, container,
            false
        )

        setupViewModel()
        setupViews()
        notesDatabase = NotesDatabase.getInstance(context!!)
        return binding.root
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = NotesViewModelFactory(dataSource, application)
        notesViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(NotesViewModel::class.java)
    }

    private fun setupViews() {
        binding.apply {
            noteTitle = binding.noteTitleEditText.text.toString()
            noteBody = binding.noteBodyEditText.text.toString()
            binding.invalidateAll()
            binding.saveNotesButton.setOnClickListener { view: View ->
                saveNotes()
            }
        }
    }

    private fun saveNotes() {
        noteTitle = binding.noteTitleEditText.text.toString()
        noteBody = binding.noteBodyEditText.text.toString()
        val notes = Notes(0, noteTitle, noteBody)
        notesViewModel.addNote(notes)
        Toast.makeText(context, "Your Note has been Saved to the Database. ", Toast.LENGTH_SHORT)
            .show()
        view?.findNavController()?.navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
    }

}

