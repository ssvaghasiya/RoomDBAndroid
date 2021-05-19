package com.example.roomdb

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewNoteActivity : AppCompatActivity() {

    companion object {
        val NOTE_ADDED = "new_note"
    }

    private var etNewNote: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        etNewNote = findViewById(R.id.etNewNote)

        val button: Button = findViewById(R.id.bAdd)
        button.setOnClickListener {
            val resultIntent = Intent()
            if (TextUtils.isEmpty(etNewNote?.getText())) {
                setResult(RESULT_CANCELED, resultIntent)
            } else {
                val note = etNewNote?.getText().toString()
                resultIntent.putExtra(NOTE_ADDED, note)
                setResult(RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}