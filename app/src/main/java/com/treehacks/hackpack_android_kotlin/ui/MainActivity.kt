package com.treehacks.hackpack_android_kotlin.ui


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

import com.treehacks.hackpack_android_kotlin.App
import com.treehacks.hackpack_android_kotlin.R
import com.treehacks.hackpack_android_kotlin.data.repository.INotesRepository
import com.treehacks.hackpack_android_kotlin.data.model.Note

class MainActivity : AppCompatActivity() {

    internal var TAG = MainActivity::class.java.simpleName

    private var noteListView: ListView? = null

    private var notesRepo: INotesRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteListView = findViewById(R.id.noteListView)
        val takeNoteView = findViewById<TextView>(R.id.takeNote)

        //You can use below AudioNoteView to add audio note functionality to the app
        //        ImageView takeAudioNoteView = findViewById(R.id.takeAudioNoteView);

        notesRepo = (application as App).notesRepository

        takeNoteView.setOnClickListener { startTakeNoteActivity() }

        populateNotes()
    }

    override fun onResume() {
        super.onResume()
        populateNotes()
    }

    private fun startTakeNoteActivity() {
        val intent = Intent(this, NotesActivity::class.java)
        startActivity(intent)
    }

    private fun startTakeNoteActivity(note: Note) {
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    private fun populateNotes() {

        val noteList = notesRepo!!.getNotesList()

        val noteArrayAdapter = ArrayAdapter(
                this, R.layout.note_card_view, R.id.note, noteList)

        noteArrayAdapter.notifyDataSetChanged()

        noteListView!!.adapter = noteArrayAdapter
        noteListView!!.setPadding(8, 8, 8, 8)
        noteListView!!.dividerHeight = 8

        noteListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val note = parent.adapter.getItem(position) as Note
            startTakeNoteActivity(note)
        }
    }

    override fun onStop() {
        super.onStop()
        (application as App).saveNotes()
    }
}
