package com.treehacks.hackpack_android_kotlin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.treehacks.hackpack_android_kotlin.data.model.Note
import com.treehacks.hackpack_android_kotlin.data.repository.DummyNotesRepository
import com.treehacks.hackpack_android_kotlin.data.repository.INotesRepository
import com.treehacks.hackpack_android_kotlin.data.repository.LocalNotesRepository

class App : Application() {

    internal var testingMode = false

    val prefKey = BuildConfig.APPLICATION_ID + "notesList"

    val notesRepository: INotesRepository
        get() = if (testingMode) {
            DummyNotesRepository.getInstance(this)
        } else LocalNotesRepository.getInstance(this)

    val sharedPrefs: SharedPreferences
        get() = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    override fun onCreate() {
        super.onCreate()
    }


    fun saveNotes() {
        val noteList = notesRepository.getNotesList()
        val gson = Gson()
        val json = gson.toJson(noteList)
        sharedPrefs.edit()
                .putString(prefKey, json)
                .apply()
    }
}
