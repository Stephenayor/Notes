package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.notes.databinding.FragmentNoteListBinding
import com.google.android.material.navigation.NavigationView


class NoteListFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        inflater.inflate(R.layout.fragment_note_list, container, false)
        val binding = DataBindingUtil.inflate<FragmentNoteListBinding>(inflater, R.layout.fragment_note_list, container,
            false)
//
//
//        binding.noteFragment = NoteListFragment()
//        var drawerLayout2: DrawerLayout = binding.drawerLayout
//        actionBarToggle = ActionBarDrawerToggle(activity, drawerLayout2, 0, 0)
//        drawerLayout.addDrawerListener(actionBarToggle)
//        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBarToggle.syncState()
        return binding.root
    }

}