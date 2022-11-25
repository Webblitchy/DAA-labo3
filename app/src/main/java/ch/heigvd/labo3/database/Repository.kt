package ch.heigvd.labo3.database

import ch.heigvd.labo3.models.Note
import kotlin.concurrent.thread

class Repository(private val noteDAO: NoteDAO) {
    val allNotes = noteDAO.getAll() //: LiveData<List<Note>>

    fun insertNote(note : Note) {
        thread {
            noteDAO.insert(note)
        }
    }

    fun deleteAllNotes() {
        thread {
            // TODO: delete all
            //noteDAO.deleteAll()
        }
    }
}
