package com.sumeyyaterzi.thenotesapp.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyaterzi.thenotesapp.MainActivity
import com.sumeyyaterzi.thenotesapp.R
import com.sumeyyaterzi.thenotesapp.adapter.NoteAdapter
import com.sumeyyaterzi.thenotesapp.base.BaseFragment
import com.sumeyyaterzi.thenotesapp.databinding.FragmentHomeBinding
import com.sumeyyaterzi.thenotesapp.model.Note
import com.sumeyyaterzi.thenotesapp.viewmodel.NoteViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    SearchView.OnQueryTextListener {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun setupUI() {
        noteViewModel = (activity as MainActivity).noteViewModel
        setupHomeRecyclerView()

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    override fun setupObservers() {
        noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapter.differ.submitList(notes)
            updateUI(notes)
        }
    }

    private fun updateUI(notes: List<Note>?) {
        if (notes.isNullOrEmpty()) {
            binding.emptyNotesImage.visibility = View.VISIBLE
            binding.homeRecyclerView.visibility = View.GONE
        } else {
            binding.emptyNotesImage.visibility = View.GONE
            binding.homeRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupHomeRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query%"
        noteViewModel.searchNote(searchQuery).observe(viewLifecycleOwner) { notes ->
            noteAdapter.differ.submitList(notes)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { searchNote(it) }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(this)
    }
}
