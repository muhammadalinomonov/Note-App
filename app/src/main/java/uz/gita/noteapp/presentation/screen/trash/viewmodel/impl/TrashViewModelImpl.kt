package uz.gita.noteapp.presentation.screen.trash.viewmodel.impl

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import uz.gita.noteapp.R
import uz.gita.noteapp.domain.repository.AppRepository
import uz.gita.noteapp.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteapp.presentation.screen.trash.viewmodel.TrashViewModel

class TrashViewModelImpl : ViewModel(), TrashViewModel {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override val notesInTrash = repository.getNotesInTrash()

    override fun deleteNoteById(noteId: Long) {
        repository.deleteNoteById(noteId)
    }

    override fun recover(noteId: Long) {
        repository.recoverNote(noteId)
    }

    override fun showDialog(context: Context, noteId: Long, title: String) {
        val dialog = Dialog(context)

        dialog.setContentView(R.layout.dialog_custom_trash)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val recover: MaterialButton = dialog.findViewById(R.id.recover_btn)

        val completelyDelete: MaterialButton = dialog.findViewById(R.id.delete_btn)

        val titleNote: AppCompatTextView = dialog.findViewById(R.id.title)


        titleNote.text = context.getString(R.string.title, title)

        recover.setOnClickListener {
            repository.recoverNote(noteId)
            dialog.dismiss()
        }

        completelyDelete.setOnClickListener {
            repository.deleteNoteById(noteId)
            dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }

    override fun deleteNotesFromTrash() {
        repository.deleteNotesInTrash()
    }

    override fun showDeleteDialogNotesInTrash(context: Context) {
        /* val dialog = Dialog(context)

        dialog.setContentView(R.layout.dialog_custom_delete_trash)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val noBtn: MaterialButton = dialog.findViewById(R.id.no_btn)

        val deleteBtn: MaterialButton = dialog.findViewById(R.id.delete_all_notes_btn)


        noBtn.setOnClickListener {
            dialog.dismiss()
        }

        deleteBtn.setOnClickListener {*/
        repository.deleteNotesInTrash()
        /*    dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }*/
    }
}