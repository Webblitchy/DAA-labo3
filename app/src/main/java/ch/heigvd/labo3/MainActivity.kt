package ch.heigvd.labo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.labo3.models.Note

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.frag_notes)
        val adapter = RecyclerAdapterNotes()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        // Generate 10 random notes
        for (i in 0 ..9) {
            adapter.items += Note.generateRandomNote()
        }
    }
}
