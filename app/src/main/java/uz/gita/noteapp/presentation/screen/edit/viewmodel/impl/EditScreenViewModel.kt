package uz.gita.noteapp.presentation.screen.edit.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.domain.repository.AppRepository
import uz.gita.noteapp.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel
import uz.gita.noteapp.presentation.screen.edit.viewmodel.EditScreenViewModel

class EditScreenViewModelImpl:ViewModel(), EditScreenViewModel {

    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override val backToHomeScreen = MutableLiveData<Unit>()

    override fun updateNote(noteData: NoteData) {
        repository.updateNote(noteData)
        backToHomeScreen.value = Unit
    }

    override fun getRichFeatures(): List<RichFeatureModel> = repository.getRichFeatures()

}