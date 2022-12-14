package ch.heigvd.labo3.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*
import kotlin.concurrent.thread
import ch.heigvd.labo3.models.Note
import ch.heigvd.labo3.models.NoteAndSchedule
import ch.heigvd.labo3.models.Schedule


/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
@Database(
    entities = [
        Note::class, Schedule::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(CalendarConverter::class)
abstract class LaboDatabase : RoomDatabase() {
    abstract fun noteDAO(): NoteDAO
    abstract fun scheduleDAO(): ScheduleDAO

    /*
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Note ADD COLUMN author INTEGER")
        }
    }
    */

    companion object {
        private var INSTANCE: LaboDatabase? = null

        fun getDatabase(context: Context): LaboDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    LaboDatabase::class.java, "LaboDatabase.sqlite"
                )
                    //.addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .addCallback(LaboDatabaseCallBack())
                    .build()
                INSTANCE!!
            }
        }

        private class LaboDatabaseCallBack : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    val isEmpty = database.noteDAO().getCount().value == 0L
                    if (isEmpty) {
                        thread {
                            // when the database is created for the 1st time, we can, for example, populate it
                            // should be done asynchronously

                            // Generate 10 random notes
                            for (i in 0 .. 9) {
                                database.noteDAO().insert(Note.generateRandomNote())
                                val schedule = Note.generateRandomSchedule()
                                if (schedule != null) {
                                    database.scheduleDAO().insert(schedule)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class CalendarConverter {
    @TypeConverter
    fun toCalendar(dateLong: Long) =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }

    @TypeConverter
    fun fromCalendar(date: Calendar) =
        date.time.time // Long
}