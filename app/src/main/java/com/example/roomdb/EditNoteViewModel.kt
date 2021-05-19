package com.example.roomdb

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class EditNoteViewModel: AndroidViewModel {

    private val TAG = this.javaClass.simpleName
    private var noteDao: NoteDao? = null
    private var db: NoteRoomDatabase? = null

    constructor(@NonNull application: Application?): super(application!!) {
        Log.i(TAG, "Edit ViewModel")
        db = NoteRoomDatabase.getDatabase(application!!)
        noteDao = db!!.noteDao()
    }

    fun getNote(noteId: String?): LiveData<Note>? {
        return noteDao!!.getNote(noteId)
    }
}