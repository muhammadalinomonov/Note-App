package uz.gita.noteapp.domain.repository.impl

import androidx.lifecycle.LiveData
import uz.gita.noteapp.R
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.data.source.local.NoteDatabase
import uz.gita.noteapp.domain.repository.AppRepository
import uz.gita.noteapp.presentation.adapter.data.RichFeatureModel
import uz.gita.noteapp.presentation.adapter.data.RichFeatureType

class AppRepositoryImpl private constructor(): AppRepository {
    companion object{
        private lateinit var repository: AppRepositoryImpl

        fun getInstance(): AppRepositoryImpl {
            if (!(::repository.isInitialized)) {
                repository = AppRepositoryImpl()
            }
            return repository
        }
    }
    private val noteDao = NoteDatabase.getInstance().getNoteDao()

    override fun addNote(noteData: NoteData) {
        noteDao.insert(noteData.toNoteEntity())
    }

    override fun updateNote(noteData: NoteData) {
        noteDao.insert(noteData.toNoteEntity())
    }

    override fun deleteNote(noteData: NoteData) {
        noteDao.deleteNote(noteData.toNoteEntity())
    }

    override fun deleteNotes(vararg note: NoteData) {
        val list = note.map {
            it.toNoteEntity()
        }.toTypedArray()
        noteDao.deleteNotes(*list)
    }

    override fun getNotes(): LiveData<List<NoteData>> {
       return noteDao.getNotes()
    }

    override fun getNotesInTrash(): LiveData<List<NoteData>> {
        return noteDao.getNotesInTrash()
    }

    override fun noteToTrash(id: Long) {
        noteDao.noteToTrash(id)
    }

    override fun deleteNoteById(noteId: Long) {
        noteDao.deleteNoteById(noteId)
    }

    override fun recoverNote(noteId: Long) {
        noteDao.recoverNote(noteId)
    }

    override fun deleteNotesInTrash() {
        noteDao.deleteNotesFromTrash()
    }

    override fun search(search: String): List<NoteData> {
        return noteDao.search(search)
    }

    override fun pinNote(noteId: Long) {
        noteDao.pinNote(noteId)
    }

    override fun unPinNote(noteId: Long) {
        noteDao.unPinNote(noteId)
    }

    override fun getRichFeatures(): List<RichFeatureModel> = listOf(
        RichFeatureModel(
            id = 0,
            type = RichFeatureType.BOLD,
            image = R.drawable.ic_bold
        ),
        RichFeatureModel(
            id = 1,
            type = RichFeatureType.ITALIC,
            image = R.drawable.ic_italic
        ),
        RichFeatureModel(
            id = 2,
            type = RichFeatureType.SUBSCRIPT,
            image = R.drawable.ic_subscript
        ),
        RichFeatureModel(
            id = 3,
            type = RichFeatureType.SUPERSCRIPT,
            image = R.drawable.ic_superscript
        ),
        RichFeatureModel(
            id = 4,
            type = RichFeatureType.STRIKETHROUGH,
            image = R.drawable.ic_strikethrough
        ),
        RichFeatureModel(
            id = 5,
            type = RichFeatureType.UNDERLINE,
            image = R.drawable.ic_underline
        ),
        RichFeatureModel(
            id = 6,
            type = RichFeatureType.H1,
            image = R.drawable.h1
        ),
        RichFeatureModel(
            id = 7,
            type = RichFeatureType.H2,
            image = R.drawable.h2
        ),
        RichFeatureModel(
            id = 8,
            type = RichFeatureType.H3,
            image = R.drawable.h3
        ),
        RichFeatureModel(
            id = 9,
            type = RichFeatureType.H4,
            image = R.drawable.h4
        ),
        RichFeatureModel(
            id = 10,
            type = RichFeatureType.H5,
            image = R.drawable.h5
        ),
        RichFeatureModel(
            id = 11,
            type = RichFeatureType.H6,
            image = R.drawable.h6
        ),
        RichFeatureModel(
            id = 12,
            type = RichFeatureType.JUSTIFYLEFT,
            image = R.drawable.ic_justify_left
        ),
        RichFeatureModel(
            id = 13,
            type = RichFeatureType.JUSTIFYCENTER,
            image = R.drawable.ic_justify_center
        ),
        RichFeatureModel(
            id = 14,
            type = RichFeatureType.JUSTIFYRIGHT,
            image = R.drawable.ic_justify_right
        ),
    )

    override fun archiveNote(noteId: Long) {
        noteDao.archiveNote(noteId)
    }

    override fun unArchiveNote(noteId: Long) {
        noteDao.unArchiveNote(noteId)
    }

    override fun getNotesInArchive(): LiveData<List<NoteData>> {
        return noteDao.getNotesInArchive()
    }

}