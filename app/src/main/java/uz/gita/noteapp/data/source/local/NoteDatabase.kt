package uz.gita.noteapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.noteapp.data.source.local.convertor.DateConvertor
import uz.gita.noteapp.data.source.local.dao.NoteDao
import uz.gita.noteapp.data.source.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], exportSchema = true, version = 1)
@TypeConverters(DateConvertor::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        private lateinit var instance: NoteDatabase

        private const val DATABASE_NAME = "Note.db"

        fun init(context: Context) {
            if (!(::instance.isInitialized)) {
                instance = Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getInstance() = instance
    }
}