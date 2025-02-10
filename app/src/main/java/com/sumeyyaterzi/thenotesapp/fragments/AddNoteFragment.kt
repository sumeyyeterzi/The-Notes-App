package com.sumeyyaterzi.thenotesapp.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.sumeyyaterzi.thenotesapp.MainActivity
import com.sumeyyaterzi.thenotesapp.R
import com.sumeyyaterzi.thenotesapp.base.BaseFragment
import com.sumeyyaterzi.thenotesapp.databinding.FragmentAddNoteBinding
import com.sumeyyaterzi.thenotesapp.model.Note
import com.sumeyyaterzi.thenotesapp.viewmodel.NoteViewModel

class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    private lateinit var notesViewModel: NoteViewModel

    override fun setupUI() {
        notesViewModel = (activity as MainActivity).noteViewModel
    }

    override fun setupObservers() {
        // Gerekirse ViewModel veya LiveData gözlemleri buraya eklenebilir
    }

    private fun saveNote() {
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteDesc)
            notesViewModel.addNote(note)
            Toast.makeText(requireContext(), "Note Saved Successfully", Toast.LENGTH_LONG).show()
            requireView().findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(requireContext(), "Please enter note title", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_add_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote()
                true
            }
            else -> super.onMenuItemSelected(menuItem)
        }
    }
}
