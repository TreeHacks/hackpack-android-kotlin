package com.treehacks.hackpack_android_kotlin.data.repository

import android.app.Application
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.treehacks.hackpack_android_kotlin.App
import com.treehacks.hackpack_android_kotlin.data.model.Note

import java.util.ArrayList
import java.util.UUID

class LocalNotesRepository(application: Application) : INotesRepository {
    private var notes: MutableList<Note> = ArrayList()
    private val sharedPreferences: SharedPreferences
    private var prefsKey = ""

    init {
        sharedPreferences = (application as App).sharedPrefs
        prefsKey = application.prefKey
    }

    override fun getNotesList(): List<Note> {
        if (notes.size != 0) return notes
        val gson = Gson()
        val json = sharedPreferences.getString(prefsKey, "")
        if (json!!.trim { it <= ' ' }.isEmpty()) {
            return notes
        }
        val type = object : TypeToken<List<Note>>() {}.type
        notes = gson.fromJson<MutableList<Note>>(json, type)
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
        @Volatile private var INSTANCE: LocalNotesRepository? = null

        fun getInstance(application: Application): LocalNotesRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: LocalNotesRepository(application).also { INSTANCE = it }
                }
    }
}
