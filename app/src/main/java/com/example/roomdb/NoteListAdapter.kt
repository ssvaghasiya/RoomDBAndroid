package com.example.roomdb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteListAdapter :
    RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    interface OnDeleteClickListener {
        fun OnDeleteClickListener(myNote: Note?)
    }

    private var layoutInflater: LayoutInflater? = null
    private var mContext: Context? = null
    private var mNotes: List<Note>? = null
    private var onDeleteClickListener: OnDeleteClickListener? = null

    constructor(
        context: Context?,
        listener: OnDeleteClickListener?
    ) : super() {
        layoutInflater = LayoutInflater.from(context)
        mContext = context
        onDeleteClickListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteListAdapter.NoteViewHolder {
        val itemView = layoutInflater!!.inflate(R.layout.list_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (mNotes != null) {
            val note = mNotes!![position]
            holder.setData(note.mNote, position)
            holder.setListeners()
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText(R.string.no_note)
        }
    }

    override fun getItemCount(): Int {
        return mNotes?.size ?: 0
    }

    fun setNotes(notes: List<Note>) {
        mNotes = notes
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView
        private var mPosition = 0
        private val imgDelete: ImageView
        private val imgEdit: ImageView
        fun setData(note: String?, position: Int) {
            noteItemView.text = note
            mPosition = position
        }

        fun setListeners() {
            imgEdit.setOnClickListener {
                val intent = Intent(mContext, EditNoteActivity::class.java)
                intent.putExtra("note_id", mNotes?.get(mPosition)?.id)
                (mContext as Activity).startActivityForResult(
                    intent,
                    MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE
                )
            }
            imgDelete.setOnClickListener {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener?.OnDeleteClickListener(mNotes?.get(mPosition))
                }
            }
        }

        init {
            noteItemView = itemView.findViewById(R.id.txvNote)
            imgDelete = itemView.findViewById(R.id.ivRowDelete)
            imgEdit = itemView.findViewById(R.id.ivRowEdit)
        }
    }
}