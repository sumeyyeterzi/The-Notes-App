package com.sumeyyaterzi.thenotesapp.base

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB)
    : Fragment(), MenuProvider {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupUI()
        setupObservers()
    }

    abstract fun setupUI()
    abstract fun setupObservers()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Bellek sızıntısını önlemek için
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()  // Önceki menüyü temizle
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false  // Varsayılan olarak hiçbir menü işlemi yapılmaz
    }
}
