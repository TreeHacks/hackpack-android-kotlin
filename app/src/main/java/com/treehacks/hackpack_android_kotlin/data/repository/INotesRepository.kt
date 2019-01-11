package com.treehacks.hackpack_android_kotlin.data.repository

import com.treehacks.hackpack_android_kotlin.data.model.Note

interface INotesRepository {
    fun getNotesList() : List<Note>
    fun add(note: Note)
    fun update(note: Note)
    fun getNoteById(id: String): Note?
    fun deleteNoteById(id: String): Boolean
}
