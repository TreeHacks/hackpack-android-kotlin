package com.treehacks.hackpack_android_kotlin.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import com.treehacks.hackpack_android_kotlin.App
import com.treehacks.hackpack_android_kotlin.R
import com.treehacks.hackpack_android_kotlin.data.repository.INotesRepository
import com.treehacks.hackpack_android_kotlin.data.model.Note

class NotesActivity : AppCompatActivity() {

    private var titleView: EditText? = null
    private var noteView: EditText? = null
    private var notesRepo: INotesRepository? = null
    private var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val bundle = intent.extras

        if (bundle != null) {
            noteId = bundle.getString("noteId")
        }

        notesRepo = (application as App).notesRepository

        titleView = findViewById(R.id.noteTitle)
        noteView = findViewById(R.id.note)

        val imageButton = findViewById<Button>(R.id.imageButton)

        imageButton.setText(R.string.add_note)

        if (noteId != null) {
            imageButton.setText(R.string.update_note)
        }

        imageButton.setOnClickListener(View.OnClickListener {
            addNote()
            startMainActivity()
            return@OnClickListener
        })


        if (noteId != null) {
            val note = notesRepo!!.getNoteById(noteId!!)
            titleView!!.setText(note?.title)
            noteView!!.setText(note?.note)
            return
        }

        noteView!!.requestFocus()

    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun addNote() {
        if (noteId != null) {
            updateNote()
            return
        }
        val titleValue = titleView!!.text.toString()
        val notesValue = noteView!!.text.toString()

        if (titleValue.isEmpty() && notesValue.isEmpty()) return

        val note = Note(titleValue, notesValue)
        notesRepo!!.add(note)
    }

    private fun updateNote() {
        val titleValue = titleView!!.text.toString()
        val notesValue = noteView!!.text.toString()
        val note = notesRepo!!.getNoteById(noteId!!)
        note?.update(titleValue, notesValue)
    }

    override fun onBackPressed() {
        addNote()
        super.onBackPressed()
    }
}
