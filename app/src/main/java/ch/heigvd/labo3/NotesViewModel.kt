package ch.heigvd.labo3

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.labo3.database.NoteAndSchedule
import ch.heigvd.labo3.database.Repository
import ch.heigvd.labo3.models.Note
/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
class NotesViewModel(private val repository: Repository) : ViewModel() {
    fun getAllNotes(): LiveData<List<NoteAndSchedule>> {
        return repository.getAllNotes()
    }

    fun generateRandomNote() {
        insertNote(Note.generateRandomNote())
    }

    fun insertNote(note: Note) {
        repository.insertNote(note)
    }

    fun deleteAllNote() {
        repository.deleteAllNotes()
    }
}

class NotesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}