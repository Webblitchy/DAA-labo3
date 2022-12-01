package ch.heigvd.labo3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.labo3.models.Note
import ch.heigvd.labo3.models.Type

class RecyclerAdapterNotes (_items : List<Note> = listOf()) : RecyclerView.Adapter<RecyclerAdapterNotes.ViewHolder>() {
    var items = listOf<Note>()

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
        return items[position].type.ordinal
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

        fun bind(note: Note) {
            // TODO: set title, schedule logo, ...
            noteTitle.setText(note.title)
            noteText.setText(note.text)
            //note.state
            when (note.type) {
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
        }
    }
}


class NotesDiffCallback(private val oldList: List<Note>, private val newList: List<Note>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].noteId == newList[newItemPosition].noteId
    }
    override fun areContentsTheSame(oldItemPosition : Int, newItemPosition : Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class && old.state == new.state
    }
}