package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        binding.recycleView.adapter = adapter

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View) {
        val noteText = binding.input.text.toString()
        if(noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this, "${noteText} Inserted", Toast.LENGTH_LONG).show()
            binding.input.editableText.clear()
        }
    }

}