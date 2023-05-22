package uz.gita.noteapp.presentation.screen.archive.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.noteapp.data.model.NoteData

interface ArchiveViewModel {
    val notesInArchive: LiveData<List<NoteData>>
    fun deleteNoteById(noteId: Long)
    fun recoverFromArchive(noteId: Long)
    fun showDialog(context: Context, noteId: Long, title: String)
}