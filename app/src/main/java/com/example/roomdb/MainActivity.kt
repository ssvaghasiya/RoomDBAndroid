package com.example.roomdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity(), NoteListAdapter.OnDeleteClickListener {

    private val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
    companion object{
        val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }
    private val TAG = this.javaClass.simpleName
    private var noteViewModel: NoteViewModel? = null
    private var noteListAdapter: NoteListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        noteListAdapter = NoteListAdapter(this, this)
        recyclerView.setAdapter(noteListAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE)
        })

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        noteViewModel?.getAllNotes()
            ?.observe(this, object : Observer<List<Note>?> {
                override fun onChanged(notes: List<Note>?) {
                    noteListAdapter?.setNotes(notes!!)
                }
            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            val note_id = UUID.randomUUID().toString()
            val note = Note(note_id, data?.getStringExtra(NewNoteActivity.NOTE_ADDED))
            noteViewModel?.insert(note)
            Toast.makeText(
                applicationContext,
                R.string.saved,
                Toast.LENGTH_LONG
            ).show()
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to update the note
            val note = Note(
                data?.getStringExtra(EditNoteActivity.NOTE_ID),
                data?.getStringExtra(EditNoteActivity.UPDATED_NOTE)
            )
            noteViewModel?.update(note)
            Toast.makeText(
                applicationContext,
                R.string.updated,
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                applicationContext,
                R.string.not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun OnDeleteClickListener(myNote: Note?) {
        // Code for Delete operation
        noteViewModel?.delete(myNote)
    }
}