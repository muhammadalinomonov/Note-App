package uz.gita.noteapp.presentation.screen.addnote.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.domain.repository.AppRepository
import uz.gita.noteapp.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel
import uz.gita.noteapp.presentation.screen.addnote.viewmodel.AddViewModel

class AddViewModelImpl :ViewModel(), AddViewModel{
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override val closeAddNoteScreen = MutableLiveData<Unit>()

    override fun addNote(noteData: NoteData) {
        repository.addNote(noteData)
    }

    override fun closeAddNote() {
        closeAddNoteScreen.value = Unit
    }

    override fun getRichFeatures(): List<RichFeatureModel> = repository.getRichFeatures()

}