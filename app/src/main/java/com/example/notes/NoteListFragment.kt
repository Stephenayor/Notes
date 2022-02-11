package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapter.NoteListAdapter
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory


class NoteListFragment : Fragment(), NoteListAdapter.NoteClickInterface {
    private lateinit var notesViewModel: NotesViewModel
    lateinit var notesList: List<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentNoteListBinding>(
            inflater, R.layout.fragment_note_list, container,
            false)

        setupViewModel()
        //navigate to details Fragment
        binding.floatingActionButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_noteListFragment_to_noteDetailsFragment)
        }
        //Set up the Adapter
        val noteListAdapter = noteListAdapter(binding)
        val swipe = ItemTouchHelper(initSwipeToDelete())
        swipe.attachToRecyclerView(binding.notesRecyclerView)
        notesViewModel.allSavedNotes.observe(this, Observer { list ->
            list?.let {
                noteListAdapter.noteList = list
                notesList = list
            }
        })
        return binding.root
    }

    private fun noteListAdapter(binding: FragmentNoteListBinding): NoteListAdapter {
        val noteListAdapter = NoteListAdapter(this)
        binding.notesRecyclerView.adapter = noteListAdapter
        binding.notesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        return noteListAdapter
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(context!!).notesDao
        val viewModelFactory = NotesViewModelFactory(dataSource, application)
        notesViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(NotesViewModel::class.java)
    }

    override fun onNoteClick(note: Notes) {
        val navDirection = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailsFragment(note)
        findNavController().navigate(navDirection)
    }

    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        //Swipe recycler view items on RIGHT
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                notesViewModel.deleteNote(notesList[position])
                Toast.makeText(activity, "NOTE DELETED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
