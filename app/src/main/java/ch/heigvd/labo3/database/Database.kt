package ch.heigvd.labo3.database

import android.content.Context
import androidx.room.*
import java.util.*


typealias Note = ch.heigvd.labo3.models.Note
typealias Schedule = ch.heigvd.labo3.models.Schedule
typealias NoteAndSchedule = ch.heigvd.labo3.models.NoteAndSchedule

@Database(entities = [
    Note::class, Schedule::class, NoteAndSchedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(CalendarConverter::class)
abstract class LaboDatabase : RoomDatabase() {
    abstract fun noteDAO() : NoteDAO

    /*
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Note ADD COLUMN author INTEGER")
        }
    }
    */

    companion object {
        private var INSTANCE : LaboDatabase? = null

        fun getDatabase(context: Context) : LaboDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    LaboDatabase::class.java, "LaboDatabase.sqlite")
                    //.addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    //.addCallback(RDatabaseCallBack())
                    .build()
                INSTANCE!!
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