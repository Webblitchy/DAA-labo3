package ch.heigvd.labo3.database

import android.util.Log
import androidx.lifecycle.LiveData
import ch.heigvd.labo3.models.NoteAndSchedule
import kotlin.concurrent.thread

/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
class Repository(private val noteDAO: NoteDAO, private val scheduleDAO: ScheduleDAO) {
    fun getAllNotes() : LiveData<List<NoteAndSchedule>> {
        return noteDAO.getAll()
    }

    fun insertNote(noteAndSchedule: NoteAndSchedule) {
        thread {
            val idNote = noteDAO.insert(noteAndSchedule.note)
            if (noteAndSchedule.schedule != null) {
                noteAndSchedule.schedule.ownerId = idNote
                Log.w("oui", noteAndSchedule.schedule.date.toString())
                scheduleDAO.insert(noteAndSchedule.schedule)
            }
        }
    }

    fun deleteAllNotes() {
        thread {
            noteDAO.deleteAll()
            scheduleDAO.deleteAll()
        }
    }
}
