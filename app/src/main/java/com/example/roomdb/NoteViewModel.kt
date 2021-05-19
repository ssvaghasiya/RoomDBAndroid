package com.example.roomdb

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel : AndroidViewModel {

    private val TAG = this.javaClass.simpleName
    private var noteDao: NoteDao? = null
    private var noteDB: NoteRoomDatabase? = null
    private var mAllNotes: LiveData<List<Note>>? = null

    constructor(application: Application?) : super(application!!) {

        noteDB = NoteRoomDatabase.getDatabase(application)
        noteDao = noteDB?.noteDao()
        mAllNotes = noteDao?.getAllNotes()
    }

    fun insert(note: Note?) {
        InsertAsyncTask(noteDao)
            .execute(note)
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return mAllNotes
    }

    fun update(note: Note?) {
        UpdateAsyncTask(noteDao)
            .execute(note)
    }

    fun delete(note: Note?) {
        DeleteAsyncTask(noteDao)
            .execute(note)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel Destroyed")
    }


    open inner class OperationsAsyncTask :
        AsyncTask<Note?, Void?, Void?> {
        var mAsyncTaskDao: NoteDao

        constructor(dao: NoteDao?) {
            mAsyncTaskDao = dao!!
        }

        override fun doInBackground(vararg params: Note?): Void? {
            return null
        }
    }

    inner class InsertAsyncTask :
        OperationsAsyncTask {

        constructor(mNoteDao: NoteDao?) : super(mNoteDao) {

        }

        override fun doInBackground(vararg notes: Note?): Void? {
            mAsyncTaskDao.insert(notes[0])
            return null
        }
    }

    inner class UpdateAsyncTask :
        OperationsAsyncTask {

        constructor(noteDao: NoteDao?) : super(noteDao) {

        }

        override fun doInBackground(vararg notes: Note?): Void? {
            mAsyncTaskDao.update(notes[0])
            return null
        }
    }


    inner class DeleteAsyncTask : OperationsAsyncTask {

        constructor(noteDao: NoteDao?) : super(noteDao) {

        }

        override fun doInBackground(vararg notes: Note?): Void? {
            mAsyncTaskDao.delete(notes[0])
            return null
        }
    }

}