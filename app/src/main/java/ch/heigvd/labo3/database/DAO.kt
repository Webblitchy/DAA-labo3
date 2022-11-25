package ch.heigvd.labo3.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.heigvd.labo3.models.Note
import ch.heigvd.labo3.models.NoteAndSchedule
import ch.heigvd.labo3.models.Schedule
import ch.heigvd.labo3.models.State

@Dao
interface DAO_Note {
    @Insert
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM Note")
    fun getAll(): LiveData<List<Note>>
}

@Dao
interface DAO_NoteAndSchedule {
    @Insert
    fun insert(noteAndSchedule: NoteAndSchedule): Long

    @Update
    fun update(noteAndSchedule: NoteAndSchedule)

    @Delete
    fun delete(noteAndSchedule: NoteAndSchedule)
}

@Dao
interface DAO_Schedule {
    @Insert
    fun insert(schedule: Schedule): Long

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)
}
