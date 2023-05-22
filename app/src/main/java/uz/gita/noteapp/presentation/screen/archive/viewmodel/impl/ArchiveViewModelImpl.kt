package uz.gita.noteapp.presentation.screen.archive.viewmodel.impl

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.gita.noteapp.R
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.domain.repository.AppRepository
import uz.gita.noteapp.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteapp.presentation.screen.archive.viewmodel.ArchiveViewModel

class ArchiveViewModelImpl:ViewModel(), ArchiveViewModel {
    private val repository:AppRepository = AppRepositoryImpl.getInstance()
    override val notesInArchive: LiveData<List<NoteData>> = repository.getNotesInArchive()

    override fun deleteNoteById(noteId: Long) {
        repository.noteToTrash(noteId)
    }

    override fun recoverFromArchive(noteId: Long) {
        repository.unArchiveNote(noteId)
    }

    override fun showDialog(context: Context, noteId: Long, title: String) {
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(R.layout.bottom_sheet_dialog)

        dialog.findViewById<TextView>(R.id.text_archive)?.text = "Unarchive"
        dialog.findViewById<ImageView>(R.id.archiveImg)?.setImageResource(R.drawable.unarchiev)
        dialog.findViewById<LinearLayoutCompat>(R.id.linePin)!!.visibility = View.GONE

        dialog.findViewById<LinearLayoutCompat>(R.id.lineDelete)!!.setOnClickListener {
            repository.deleteNoteById(noteId)
            dialog.dismiss()
        }
        dialog.findViewById<LinearLayoutCompat>(R.id.lineArchive)!!.setOnClickListener {
            repository.unArchiveNote(noteId)
            dialog.dismiss()
        }
        dialog.show()

    }
}