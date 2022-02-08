package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Notes
import com.example.notes.R
import kotlinx.android.synthetic.main.fragment_note_details.*
import kotlinx.android.synthetic.main.fragment_note_details.view.*
import kotlinx.android.synthetic.main.note_item_view.view.*
import java.util.*

class NoteListAdapter: RecyclerView.Adapter<NoteListAdapter.NoteItemViewHolder>(){
    var noteList: List<Notes?> = listOf<Notes>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        return NoteItemViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.note_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val note = noteList?.get(position)
        if (note != null) {
            holder?.noteTitle.text = note.title
            holder?.noteBody.text = note.body
        }
    }

    override fun getItemCount(): Int = noteList?.size

    class NoteItemViewHolder( binding: View,) : RecyclerView.ViewHolder(binding) {

        val noteTitle = binding.note_title_itemView
        val noteBody = binding.note_body_item_view

}
}

