package ch.heigvd.iict.and.labo4.models

import androidx.room.Embedded
import androidx.room.Relation
import ch.heigvd.labo3.models.Note

data class NoteAndSchedule (
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "ownerId"
    )
    val schedule: Schedule?
)
