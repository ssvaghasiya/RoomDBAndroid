package com.example.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note?)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>?

    @Query("SELECT * FROM notes WHERE id=:noteId")
    fun getNote(noteId: String?): LiveData<Note>?

    @Update
    fun update(note: Note?)

    @Delete
    fun delete(note: Note?): Int
}