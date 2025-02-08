package com.sumeyyaterzi.thenotesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sumeyyaterzi.thenotesapp.database.NoteDatabase
import com.sumeyyaterzi.thenotesapp.repository.NoteRepository
import com.sumeyyaterzi.thenotesapp.viewmodel.NoteViewModel
import com.sumeyyaterzi.thenotesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        }


    private fun setUpViewModel() {

        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]

    }
    }
