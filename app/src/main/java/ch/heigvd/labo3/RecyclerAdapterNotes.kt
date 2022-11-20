package ch.heigvd.labo3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.labo3.models.Note
import ch.heigvd.labo3.models.Type

class RecyclerAdapterNotes (_items : List<Note> = listOf()) : RecyclerView.Adapter<RecyclerAdapterNotes.ViewHolder>() {
    var items = listOf<Note>()

    set(value) {
        field = value
        // TODO: use diffItems.dispatchUpdatesTo(this)
        notifyDataSetChanged()
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
        val image = view.findViewById<ImageView>(R.id.image)

        fun bind(note: Note) {
            // TODO: set title, schedule logo, ...
            when (note.type) {
                Type.NONE -> {
                    image.setImageResource(R.drawable.note)
                }
                Type.TODO -> {
                    image.setImageResource(R.drawable.todo)
                }
                Type.SHOPPING -> {
                    image.setImageResource(R.drawable.shopping)
                }
                Type.WORK -> {
                    image.setImageResource(R.drawable.work)
                }
                Type.FAMILY -> {
                    image.setImageResource(R.drawable.family)
                }
            }
        }
    }
}