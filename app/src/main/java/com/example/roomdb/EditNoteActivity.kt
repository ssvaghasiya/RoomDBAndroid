package com.example.roomdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class EditNoteActivity : AppCompatActivity() {
    companion object {
        val NOTE_ID = "note_id"
        val UPDATED_NOTE = "note_text"
    }

    private var etNote: EditText? = null
    private var bundle: Bundle? = null
    private var noteId: String? = null
    private var note: LiveData<Note>? = null

    var noteModel: EditNoteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        etNote = findViewById(R.id.etNote)

        bundle = intent.extras

        if (bundle != null) {
            noteId = bundle?.getString("note_id")
        }

        noteModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
        note = noteModel?.getNote(noteId)
        note?.observe(this, object : Observer<Note?> {
            override fun onChanged(note: Note?) {
                etNote?.setText(note?.mNote)
            }
        })
    }

    fun updateNote(view: View?) {
        val updatedNote = etNote!!.text.toString()
        val resultIntent = Intent()
        resultIntent.putExtra(NOTE_ID, noteId)
        resultIntent.putExtra(UPDATED_NOTE, updatedNote)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    fun cancelUpdate(view: View?) {
        finish()
    }
}