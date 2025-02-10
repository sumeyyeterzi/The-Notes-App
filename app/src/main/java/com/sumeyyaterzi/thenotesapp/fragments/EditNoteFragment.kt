package com.sumeyyaterzi.thenotesapp.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sumeyyaterzi.thenotesapp.MainActivity
import com.sumeyyaterzi.thenotesapp.R
import com.sumeyyaterzi.thenotesapp.base.BaseFragment
import com.sumeyyaterzi.thenotesapp.databinding.FragmentEditNoteBinding
import com.sumeyyaterzi.thenotesapp.model.Note
import com.sumeyyaterzi.thenotesapp.viewmodel.NoteViewModel

class EditNoteFragment : BaseFragment<FragmentEditNoteBinding>(FragmentEditNoteBinding::inflate) {

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun setupUI() {
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener { saveNote() }
    }

    override fun setupObservers() {
        // Gerekirse ViewModel gözlemleri eklenebilir
    }

    private fun saveNote() {
        val noteTitle = binding.editNoteTitle.text.toString().trim()
        val noteDesc = binding.editNoteDesc.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(currentNote.id, noteTitle, noteDesc)
            notesViewModel.updateNote(note)
            findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            Toast.makeText(requireContext(), "Please enter note title", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> super.onMenuItemSelected(menuItem)
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_LONG).show()
                findNavController().popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }
}
