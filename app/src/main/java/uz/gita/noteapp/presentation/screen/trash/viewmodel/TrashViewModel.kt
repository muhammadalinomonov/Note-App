package uz.gita.noteapp.presentation.screen.trash.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.noteapp.data.model.NoteData

interface TrashViewModel {
    val notesInTrash: LiveData<List<NoteData>>
    fun deleteNoteById(noteId: Long)
    fun recover(noteId: Long)
    fun showDialog(context: Context, noteId: Long, title: String)
    fun deleteNotesFromTrash()
    fun showDeleteDialogNotesInTrash(context: Context)
}