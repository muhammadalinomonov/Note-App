package uz.gita.noteapp.data.model

import androidx.room.ColumnInfo
import uz.gita.noteapp.data.source.local.entity.NoteEntity
import java.io.Serializable

data class NoteData(
    val id: Long = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "on_trash")
    val onTrash: Int = 0,
    @ColumnInfo(name = "is_pin")
    val isPin: Int,
    val is_archive:Int = 0
) : Serializable {
    fun toNoteEntity(): NoteEntity = NoteEntity(
        id, title, content, createdAt, onTrash, isPin
    )
}
