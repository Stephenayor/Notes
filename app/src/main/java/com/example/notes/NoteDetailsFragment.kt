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
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteDetailsBinding
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsFragment() : Fragment() {
    private lateinit var noteTitle: String
    private lateinit var noteBody: String
    private var noteID = -1
    private lateinit var binding: FragmentNoteDetailsBinding
    private lateinit var notesDatabase: NotesDatabase
    lateinit var notesViewModel: NotesViewModel
    lateinit var notesViewModel2: NotesViewModel
    private  var noteDetails: Int = 0
    lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate<FragmentNoteDetailsBinding>(
             inflater,
             R.layout.fragment_note_details, container,
             false
         )
        binding.root.setBackgroundColor(Color.WHITE)
        if (noteID != -1) {
        bundle = this.arguments!!
            setupUpdate()
        } else {
            setupViews()

        }

        notesDatabase = NotesDatabase.getInstance(context!!)
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = NotesViewModelFactory(dataSource, application)
        notesViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(NotesViewModel::class.java)
        return binding.root
    }

    private fun setupUpdate() {
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = NotesViewModelFactory(dataSource, application)
         notesViewModel2 =
            ViewModelProvider(
                this, viewModelFactory
            ).get(NotesViewModel::class.java)
        val notes = NotesDatabase.getInstance(application).notesDao.getNoteById(noteID)
        noteID =
            bundle.getInt("notes")!!
         noteTitle = notes.title.toString()
        binding.noteTitleEditText.setText(noteTitle)
        binding.saveNotesButton.setText ("Update Note")
        binding.saveNotesButton.setOnClickListener (View.OnClickListener {
            updateNote(notes)
        })

        noteBody = notes.body.toString()
        binding.apply {
            notes.title = binding.noteTitleEditText.text.toString()
            notes.body = binding.noteBodyEditText.text.toString()
//            binding.saveNotesButton.setText ("Update Note")
//            binding.saveNotesButton.setOnClickListener (View.OnClickListener {
//                updateNote(notes)
//            })

    }}

    private fun updateNote(notesDetails: Notes) {
        notesDetails.title = binding.noteTitleEditText.text.toString()
        notesDetails.body = binding.noteBodyEditText.text.toString()
        var notes = Notes(null, notesDetails.title, notesDetails.body)
        notesViewModel.updateNote(notes)
        Toast.makeText(context, "Note Updated. ", Toast.LENGTH_SHORT).show()
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
        var notes = Notes(noteID, noteTitle, noteBody)
        notesViewModel.addNote(notes)
        Toast.makeText(context, "Your Note has been Saved to the Database. ", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
    }

}