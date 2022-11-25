package ch.heigvd.labo3.database

import ch.heigvd.labo3.models.Note
import kotlin.concurrent.thread

class Repository(private val noteDAO: NoteDAO) {
    val allNotes = noteDAO.getAll() //: LiveData<List<Note>>

    fun insertNote(vararg note : Note) {
        // on doit effectuer les opérations sur les données dans un thread, ou une coroutine
        thread {
            //* est le spread operator, pour passer un array à une fonction acceptant un vararg
            // TODO: insert
            //noteDAO.insert(*note)
        }
    }

    fun deleteAllNotes() {
        thread {
            // TODO: delete all
            //noteDAO.deleteAll()
        }
    }
}
