package com.example.roomdb

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note {
    @PrimaryKey
    @NonNull
    var id: String? = null

    @NonNull
    @ColumnInfo(name = "note")
    var mNote: String? = null

    constructor (id: String?, note: String?) {
        this.id = id
        this.mNote = note
    }
}
