package ch.heigvd.labo3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.labo3.*
import java.util.*

/*
 * Authors: Eliott Chytil, Maxim Golay & Lucien Perregaux
 */
/**
 * A simple [Fragment] subclass.
 * Use the [Notes.newInstance] factory method to
 * create an instance of this fragment.
 */
class Notes : Fragment() {
    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as LaboApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.view_notes)
        val adapter = RecyclerAdapterNotes()

        notesViewModel.getAllNotes().observe(viewLifecycleOwner){
            adapter.items = it
        }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Notes.
         */
        @JvmStatic
        fun newInstance() = Notes()
    }
}