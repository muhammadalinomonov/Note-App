package uz.gita.noteapp.presentation.screen.edit.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel

interface EditScreenViewModel {
    val backToHomeScreen: LiveData<Unit>
    fun updateNote(noteData: NoteData)
    fun getRichFeatures() : List<RichFeatureModel>
}