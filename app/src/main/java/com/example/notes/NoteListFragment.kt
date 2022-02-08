package com.example.notes

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapter.NoteListAdapter
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import com.google.android.material.navigation.NavigationView


 class NoteListFragment : Fragment() {
    private lateinit var drawerLayouabstractt: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var notesViewModel: NotesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNoteListBinding>(
            inflater, R.layout.fragment_note_list, container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(context!!).notesDao
        val viewModelFactory = NotesViewModelFactory(dataSource, application)
        notesViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(NotesViewModel::class.java)
        //navigate to details Fragment
        binding.floatingActionButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_noteListFragment_to_noteDetailsFragment)
        }
        //Set up the Adapter
        val noteListAdapter = NoteListAdapter()
        binding.notesRecyclerView.adapter = noteListAdapter
//        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
//        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
//            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
//        binding.notesRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.notesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL)
        )
        notesViewModel.allSavedNotes.observe(this, Observer { list ->
            list?.let {
                noteListAdapter.noteList = list
            }
        })

        return binding.root
    }
}
