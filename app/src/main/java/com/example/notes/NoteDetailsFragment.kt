package com.example.notes

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
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteDetailsBinding
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_details.*

class NoteDetailsFragment() : Fragment() {
    private lateinit var noteTitle: String
    private lateinit var noteBody: String
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

        checkForNoteParcel()
        notesDatabase = NotesDatabase.getInstance(context!!)
        setupViewModel()
        return binding.root
    }

    private fun checkForNoteParcel() {
        val noteArgs = arguments?.let { NoteDetailsFragmentArgs.fromBundle(it) }
        if (noteArgs != null) {
            binding.root.setBackgroundColor(Color.WHITE)
            noteDetails = noteArgs.note
            setupUpdate()
        } else {
            setupViews()
        }
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

    private fun setupUpdate() {
        noteTitle = noteDetails.title.toString()
        noteBody = noteDetails.body.toString()
        binding.noteTitleEditText.setText(noteTitle)
        binding.noteBodyEditText.setText(noteBody)
        binding.saveNotesButton.setText("Update Note")
        binding.saveNotesButton.setOnClickListener { view: View ->
            updateNote(noteDetails)
        }
    }

    private fun updateNote(notesDetails: Notes) {
        notesDetails.title = binding.noteTitleEditText.text.toString()
        notesDetails.body = binding.noteBodyEditText.text.toString()
        var notes = Notes(notesDetails.id, notesDetails.title, notesDetails.body)
        notesViewModel.updateNote(notes)
        Toast.makeText(context, "NOTE UPDATED", Toast.LENGTH_SHORT).show()
        val navDirection =
            NoteDetailsFragmentDirections.actionNoteDetailsFragmentToNoteListFragment()
        findNavController().navigate(navDirection)
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

