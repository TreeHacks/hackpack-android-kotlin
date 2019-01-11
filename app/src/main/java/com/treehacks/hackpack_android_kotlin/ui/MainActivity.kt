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

        notesRepo = (application as App).notesRepository

        takeNoteView.setOnClickListener { startTakeNoteActivity() }

        populateNotes()
    }

    override fun onResume() {
        super.onResume()
        populateNotes()
    }

    //This is the function to start a new activity from current main activity
    private fun startTakeNoteActivity() {
        //This is the intent class which is a special object to start a new action in Android
        val intent = Intent(this, NotesActivity::class.java)
        //This is a in built function that starts a new activity as per the intent specs
        startActivity(intent)
    }

    //This is overloaded function to open note activity. This will be used for updating notes
    private fun startTakeNoteActivity(note: Note) {
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    //This is function to show current notes in a list view
    private fun populateNotes() {

        //Get reference to the Note List from notes repository
        val noteList = notesRepo!!.getNotesList()

        /**
         * This is the adapter that creates the passed view (R.layout.note_card_view)
         * and populate a Text view present in the view with the passed data objects
         **/
        val noteArrayAdapter = ArrayAdapter(
                this, R.layout.note_card_view, R.id.note, noteList)

        noteArrayAdapter.notifyDataSetChanged()

        noteListView?.apply {
            adapter = noteArrayAdapter
            setPadding(8, 8, 8, 8)
            dividerHeight = 8
        }

        noteListView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val note = parent.adapter.getItem(position) as Note
            startTakeNoteActivity(note)
        }
    }

    override fun onStop() {
        super.onStop()
        (application as App).saveNotes()
    }
}
