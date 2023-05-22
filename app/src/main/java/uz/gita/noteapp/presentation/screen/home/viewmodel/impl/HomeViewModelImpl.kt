package uz.gita.noteapp.presentation.screen.home.viewmodel.impl

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import uz.gita.noteapp.R
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.domain.repository.AppRepository
import uz.gita.noteapp.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteapp.presentation.screen.home.viewmodel.HomeViewModel

class HomeViewModelImpl:ViewModel(), HomeViewModel {

    private val repository:AppRepository = AppRepositoryImpl.getInstance()

    override var notesLiveData = repository.getNotes()

    override val openAddNoteScreenLiveData = MutableSharedFlow<Unit>()


    override val openEditNoteScreenLiveData = MutableSharedFlow<NoteData>()
    override val searchNotesLiveData = MutableLiveData<List<NoteData>>()

    override fun openAddNoteScreen() {
        viewModelScope.launch {
            openAddNoteScreenLiveData.emit(Unit)
        }
    }

    override fun showDialog(requireContext: Context, noteId: Long, title: String) {
        val dialog = Dialog(requireContext)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val noBtn: MaterialButton = dialog.findViewById(R.id.cancel_btn)
        val yesBtn: MaterialButton = dialog.findViewById(R.id.okay_btn)
        val titleNote: AppCompatTextView = dialog.findViewById(R.id.title)
        titleNote.text = requireContext.getString(R.string.title,title)

        noBtn.setOnClickListener { dialog.dismiss() }

        yesBtn.setOnClickListener {
            repository.noteToTrash(noteId)
            dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }

    override fun openEditNote(noteData: NoteData) {
        viewModelScope.launch {
            openEditNoteScreenLiveData.emit(noteData)
        }
    }

    override fun searchNote(search: String) {
        searchNotesLiveData.value = repository.search(search)
    }

    override fun getAllNotes() {
        notesLiveData = repository.getNotes()
    }

    override fun updateNote(noteData: NoteData) {
        repository.updateNote(noteData)
        getAllNotes()
    }

    override fun pinNote(noteId: Long) {
        repository.pinNote(noteId)
    }

    override fun unPinNote(noteId: Long) {
        repository.unPinNote( noteId)
    }

    override fun archiveNote(noteId: Long) {
        repository.archiveNote(noteId)
    }
}