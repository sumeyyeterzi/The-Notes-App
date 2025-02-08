package com.sumeyyaterzi.thenotesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sumeyyaterzi.thenotesapp.model.Note
import kotlin.concurrent.Volatile

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun getNoteDao():NoteDao
companion object{
    @Volatile
    private var instance:NoteDatabase?=null
    private val LOCK =Any()

    operator fun invoke(context: Context)= instance?:
    synchronized(LOCK){
        instance ?:
        createDatabse(context).also{
            instance=it

        }
    }
    private fun createDatabse(context: Context)=
        Room.databaseBuilder(

            context.applicationContext,
            NoteDatabase::class.java,
            "note_db"
        ).build()

}
}