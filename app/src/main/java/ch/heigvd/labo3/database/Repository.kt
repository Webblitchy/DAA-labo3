package ch.heigvd.labo3.database

import androidx.lifecycle.LiveData
import ch.heigvd.labo3.models.Note
import kotlin.concurrent.thread

class Repository(private val noteDAO: NoteDAO) {
    fun getAllNotes() : LiveData<List<Note>> {
        return noteDAO.getAll()
    }

    fun insertNote(note : Note) {
        thread {
            noteDAO.insert(note)
        }
    }

    fun deleteAllNotes() {
        thread {
            noteDAO.deleteAll()
        }
    }
}
