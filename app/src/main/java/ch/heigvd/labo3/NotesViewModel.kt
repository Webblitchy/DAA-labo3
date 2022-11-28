package ch.heigvd.labo3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.labo3.database.Repository

class NotesViewModel(private val repository: Repository) : ViewModel() {
    val allNotes = repository.allNotes //: LiveData<List<NoteAndSchedule>>

    fun generateANote() {
        // TODO: generate a note
    }
    fun deleteAllNote() {
        // TODO: delete all notes
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