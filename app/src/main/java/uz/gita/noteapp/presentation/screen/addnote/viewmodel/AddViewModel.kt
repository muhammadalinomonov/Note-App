package uz.gita.noteapp.presentation.screen.addnote.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel

interface AddViewModel {
    val closeAddNoteScreen: LiveData<Unit>
    fun addNote(noteData: NoteData)
    fun closeAddNote()

    fun getRichFeatures() : List<RichFeatureModel>
}