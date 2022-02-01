package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.notes.databinding.FragmentNoteListBinding


class NoteListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        inflater.inflate(R.layout.fragment_note_list, container, false)
        val binding = DataBindingUtil.inflate<FragmentNoteListBinding>(inflater, R.layout.fragment_note_list, container,
            false)
        return binding.root

    }

}