package ch.heigvd.labo3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.labo3.models.NoteAndSchedule
import ch.heigvd.labo3.models.State
import ch.heigvd.labo3.models.Type
import java.util.*
import kotlin.math.roundToInt

/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
class RecyclerAdapterNotes (_items : List<NoteAndSchedule> = listOf()) : RecyclerView.Adapter<RecyclerAdapterNotes.ViewHolder>() {
    var items = listOf<NoteAndSchedule>()

    set(value) {
        val diffCallback = NotesDiffCallback(items, value)
        val diffItems = DiffUtil.calculateDiff(diffCallback)
        field = value
        diffItems.dispatchUpdatesTo(this)
    }

    init {
        items = _items
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].note.type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteIcon = view.findViewById<ImageView>(R.id.note_icon)
        val noteTitle = view.findViewById<TextView>(R.id.note_title)
        val noteText = view.findViewById<TextView>(R.id.note_text)
        val statusIcon = view.findViewById<ImageView>(R.id.status_icon)
        val statusText = view.findViewById<TextView>(R.id.status_text)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            noteTitle.text = noteAndSchedule.note.title
            noteText.text = noteAndSchedule.note.text

            when (noteAndSchedule.note.type) {
                Type.NONE -> {
                    noteIcon.setImageResource(R.drawable.note)
                }
                Type.TODO -> {
                    noteIcon.setImageResource(R.drawable.todo)
                }
                Type.SHOPPING -> {
                    noteIcon.setImageResource(R.drawable.shopping)
                }
                Type.WORK -> {
                    noteIcon.setImageResource(R.drawable.work)
                }
                Type.FAMILY -> {
                    noteIcon.setImageResource(R.drawable.family)
                }
            }

            when (noteAndSchedule.note.state) {
                State.IN_PROGRESS -> {
                    noteIcon.setColorFilter(noteIcon.context.getColor(R.color.grey));
                }
                State.DONE -> {
                    noteIcon.setColorFilter(noteIcon.context.getColor(R.color.green));
                }
            }

            if (noteAndSchedule.schedule != null) {

                statusIcon.visibility = View.VISIBLE

                // to reset to black
                statusIcon.setColorFilter(statusIcon.context.getColor(R.color.black))

                val remainingDays = getRemainingDays(noteAndSchedule.schedule.date)
                Log.w("TAG3", remainingDays.toString())
                if (remainingDays <= 0) {
                    statusText.text = statusText.context.getString(R.string.late_text)
                    statusIcon.setColorFilter(statusIcon.context.getColor(R.color.red))
                } else if (remainingDays < 7) {
                    statusText.text = String.format("%d days", remainingDays)
                } else if (remainingDays < 30) {
                    statusText.text = String.format("%d weeks", (remainingDays / 7.0).roundToInt())
                } else {
                    statusText.text = String.format("%d months", (remainingDays / 30.0).roundToInt())
                }

            }

        }
    }
}

class NotesDiffCallback(private val oldList: List<NoteAndSchedule>, private val newList: List<NoteAndSchedule>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].note.noteId == newList[newItemPosition].note.noteId
    }
    override fun areContentsTheSame(oldItemPosition : Int, newItemPosition : Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        if (old.schedule != null && new.schedule != null) {
            // if not same ETA
            if (getRemainingDays(old.schedule.date) != getRemainingDays(new.schedule.date)) {
                return false
            }
        }
        return old.note::class == new.note::class && old.note.state == new.note.state
    }
}

private fun getRemainingDays(deadLineDate: Calendar) : Int {
    return ((deadLineDate.timeInMillis - Calendar.getInstance().timeInMillis) / 86400000).toInt()
}