package uz.gita.noteapp.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.noteapp.data.model.NoteData
import uz.gita.noteapp.data.source.local.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

    @Delete
    fun deleteNotes(vararg notes:NoteEntity)

    @Query("SELECT * FROM Notes WHERE on_trash = 0  and is_archive = 0 order by is_pin DESC")
    fun getNotes():LiveData<List<NoteData>>

    @Query("SELECT * FROM Notes where on_trash = 1")
    fun getNotesInTrash():LiveData<List<NoteData>>

    @Query("UPDATE Notes SET on_trash = 1 WHERE id =:noteId")
    fun noteToTrash(noteId:Long)

    @Query("UPDATE Notes SET on_trash = 0 WHERE id =:noteId")
    fun recoverNote(noteId:Long)

    @Query("DELETE FROM Notes WHERE id = :noteId")
    fun deleteNoteById(noteId: Long)

    @Query("DELETE FROM Notes WHERE on_trash = 1")
    fun deleteNotesFromTrash()

    @Query("SELECT * FROM Notes where title LIKE '%' || :search || '%' AND on_trash = 0 and is_archive = 0")
    fun search(search:String):List<NoteData>

    @Query("UPDATE Notes set is_pin=1 WHERE id = :noteID")
    fun pinNote(noteID: Long)

    @Query("UPDATE Notes set is_pin=0 WHERE id = :noteID")
    fun unPinNote(noteID: Long)

    @Query("UPDATE Notes set is_archive = 1 WHERE id = :noteId")
    fun archiveNote(noteId: Long)

    @Query("UPDATE Notes set is_archive = 0 WHERE id = :noteId")
    fun unArchiveNote(noteId: Long)

    @Query("SELECT * FROM Notes where is_archive = 1")
    fun getNotesInArchive():LiveData<List<NoteData>>
    /*@Query("UPDATE NOTES SET title =:title, content =:content, color =:color, created_at = :date where id = :id")
    fun updateNote(id:Long, title: String, content:String, color:Int, date:String)
*/

}