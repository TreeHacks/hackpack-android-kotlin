package com.treehacks.hackpack_android_kotlin.data.model

class Note(title: String, note: String) {

    var id = ""
    var title = ""
    var note = ""
    var isPinned = false
    var isBookmarked = false

    init {
        this.title = title
        this.note = note
    }

    fun update(title: String, note: String): Note {
        this.title = title
        this.note = note
        return this
    }

    override fun toString(): String {
        return String.format("%s\n%s", title, note)
    }
}
