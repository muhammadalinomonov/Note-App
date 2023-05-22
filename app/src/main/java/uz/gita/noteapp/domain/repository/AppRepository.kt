package uz.gita.noteapp.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel

interface AppRepository {
    fun addNote(noteData: NoteData)

    fun updateNote(noteData: NoteData)

    fun deleteNote(noteData: NoteData)

    fun deleteNotes(vararg note: NoteData)

    fun getNotes():LiveData<List<NoteData>>

    fun getNotesInTrash():LiveData<List<NoteData>>

    fun noteToTrash(id:Long)

    fun deleteNoteById(noteId:Long)

    fun recoverNote(noteId: Long)

    fun deleteNotesInTrash()

    fun search(search:String):List<NoteData>

    fun pinNote(noteId: Long)

    fun unPinNote(noteId: Long)

    //todo
    fun getRichFeatures():List<RichFeatureModel>
    fun archiveNote(noteId: Long)
    fun unArchiveNote(noteId: Long)
    fun getNotesInArchive(): LiveData<List<NoteData>>
}