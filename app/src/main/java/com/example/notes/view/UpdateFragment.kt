package com.example.notes.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.UpdateFragmentArgs
import com.example.notes.UpdateFragmentDirections
import com.example.notes.database.Notes
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentUpdateBinding
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory


class UpdateFragment : Fragment() {
    private lateinit var noteTitle: String
    private lateinit var noteBody: String
    private lateinit var notesDatabase: NotesDatabase
    lateinit var notesViewModel: NotesViewModel
    private lateinit var noteDetails: Notes
    private lateinit var binding: FragmentUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentUpdateBinding>(
            inflater, R.layout.fragment_update, container,
            false
        )

        setupViewModel()
        checkForNoteParcel()
        return binding.root
    }

    private fun checkForNoteParcel() {
        val noteArgs = UpdateFragmentArgs.fromBundle(arguments!!)
        noteDetails = noteArgs.notes
        binding.root.setBackgroundColor(Color.WHITE)
        setupUpdate()
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
        val navDirection = UpdateFragmentDirections.actionUpdateFragmentToNoteListFragment()
        findNavController().navigate(navDirection)
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
}