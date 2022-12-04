package ch.heigvd.labo3

import androidx.lifecycle.*
import ch.heigvd.labo3.models.NoteAndSchedule
import ch.heigvd.labo3.database.Repository
import ch.heigvd.labo3.models.Note
import kotlinx.coroutines.flow.combine

/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */



class NotesViewModel(private val repository: Repository) : ViewModel() {
    val sortingMethod = MutableLiveData(SortingMethod.SORT_BY_DATE)

    // Combine "sortingMethod" and "getAllNotes" in one Flow
    val sortedNotes = this
        .sortingMethod
        .asFlow()
        .combine(getAllNotes().asFlow()) { method, data ->
            when (method) {
                SortingMethod.SORT_BY_DATE ->
                    data
                    .toMutableList()
                    .sortedWith(
                        compareBy {
                            it.note.creationDate
                        }
                    )
                SortingMethod.SORT_BY_ETA ->
                    data
                    .toMutableList()
                    .sortedWith(
                        compareBy {
                            if (it.schedule != null) {
                                val s = it.schedule
                                s.date.timeInMillis
                            } else {
                                Long.MAX_VALUE
                            }
                        }
                    )
                else -> data
            }
        }
        .asLiveData()





    fun getAllNotes(): LiveData<List<NoteAndSchedule>> {
        return repository.getAllNotes()
    }

    fun generateRandomNoteAndSchedule() {
        val noteAndSchedule = NoteAndSchedule(Note.generateRandomNote(), Note.generateRandomSchedule())
        insertNoteAndSchedule(noteAndSchedule)
    }

    fun insertNoteAndSchedule(noteAndSchedule: NoteAndSchedule) {
        repository.insertNote(noteAndSchedule)
    }

    fun deleteAllNote() {
        repository.deleteAllNotes()
    }

    enum class SortingMethod {
        SORT_BY_DATE,
        SORT_BY_ETA
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