package uz.gita.noteapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.noteapp.data.model.NoteData

@Entity(tableName = "Notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
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
) {
    fun toNoteData() = NoteData(
        id, title, content, createdAt, onTrash, isPin
    )
}
