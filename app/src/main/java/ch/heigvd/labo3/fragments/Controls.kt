package ch.heigvd.labo3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ch.heigvd.labo3.LaboApp
import ch.heigvd.labo3.NotesViewModel
import ch.heigvd.labo3.NotesViewModelFactory
import ch.heigvd.labo3.R

/**
 * A simple [Fragment] subclass.
 * Use the [Controls.newInstance] factory method to
 * create an instance of this fragment.
 */
class Controls : Fragment() {
    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as LaboApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Notes.
         */
        @JvmStatic
        fun newInstance() =
            Controls()
    }
}