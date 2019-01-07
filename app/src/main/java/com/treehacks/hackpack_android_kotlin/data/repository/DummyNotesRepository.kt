package com.treehacks.hackpack_android_kotlin.data.repository

import android.app.Application
import android.content.SharedPreferences

import com.treehacks.hackpack_android_kotlin.App
import com.treehacks.hackpack_android_kotlin.data.model.Note

import java.util.ArrayList
import java.util.UUID

class DummyNotesRepository(application: Application) : INotesRepository {

    private val notes = mutableListOf<Note>()
    
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = (application as App).sharedPrefs
    }


    override fun getNotesList(): List<Note> {
        val count = 50
        if (notes.size == 0) {
            for (i in 0 until count) {
                val n = Note(UUID.randomUUID().toString(), UUID.randomUUID().toString())
                val id = UUID.randomUUID().toString()
                n.id = id
                notes.add(n)
            }
        }
        return notes
    }

    override fun add(note: Note) {
        val id = UUID.randomUUID().toString()
        note.id = id
        notes.add(note)
    }

    override fun getNoteById(id: String): Note? {
        for (note in notes) {
            if (note.id == id) {
                return note
            }
        }

        return null
    }

    override fun update(newNote: Note) {
        for (note in notes) {
            if (note.id == newNote.id) {
                note.update(newNote.title, newNote.note)
            }
        }
    }

    override fun deleteNoteById(id: String): Boolean {
        for (note in notes) {
            if (note.id == id) {
                notes.remove(note)
                return true //Success | Note with given id successfully deleted
            }
        }
        return false // Failure | Note with given id not found
    }

    companion object {
        @Volatile private var INSTANCE: DummyNotesRepository? = null

        fun getInstance(application: Application): DummyNotesRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: DummyNotesRepository(application).also { INSTANCE = it }
                }
    }

}
