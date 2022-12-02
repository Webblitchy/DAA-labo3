package ch.heigvd.labo3

import android.app.Application
import ch.heigvd.labo3.database.LaboDatabase
import ch.heigvd.labo3.database.Repository

/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
// Singleton which stocks the reference to the database
class LaboApp : Application() {
    //private val applicationScope = CoroutineScope(SupervisorJob())

    val repository by lazy {
        val database = LaboDatabase.getDatabase(this)
        Repository(database.noteDAO()/*, applicationScope*/)
    }

}
