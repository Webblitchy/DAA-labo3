package ch.heigvd.labo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels

/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
class MainActivity : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as LaboApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle item selection
            return when (item.itemId) {
                R.id.main_menu_item_generate -> {
                    notesViewModel.generateRandomNoteAndSchedule()
                    true
                }
                R.id.main_menu_item_delete -> {
                    notesViewModel.deleteAllNote()
                    true
                }
                R.id.main_menu_item_creation -> {
                    notesViewModel.sortingMethod.postValue(NotesViewModel.SortingMethod.SORT_BY_DATE)
                    true
                }
                R.id.main_menu_item_eta -> {
                    notesViewModel.sortingMethod.postValue(NotesViewModel.SortingMethod.SORT_BY_ETA)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
    }
}
