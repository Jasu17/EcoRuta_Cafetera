package com.ecoruta.cafetera.ui.finca

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecoruta.cafetera.R
import com.ecoruta.cafetera.databinding.FragmentFincaListBinding

class FincaListFragment : Fragment() {

    private var _binding: FragmentFincaListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FincaViewModel by viewModels()
    private lateinit var adapter: FincaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFincaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NO usar setSupportActionBar - causa conflicto con el menú
        binding.toolbar.inflateMenu(R.menu.menu_finca_list)

        binding.toolbar.menu.findItem(R.id.action_logout)?.icon?.setTint(
            android.graphics.Color.WHITE
        )

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_logout -> {
                    cerrarSesion()
                    true
                }
                else -> false
            }
        }

        configurarRecycler()
        observarFincas()

        binding.fabAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_form)
        }
    }

    private fun configurarRecycler() {
        adapter = FincaAdapter { finca ->
            viewModel.eliminarFinca(finca)
        }
        binding.rvFincas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFincas.adapter = adapter
    }

    private fun observarFincas() {
        viewModel.fincas.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
            binding.layoutVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
            binding.toolbar.title = if (lista.isEmpty())
                "Mis Fincas"
            else
                "Mis fincas (${lista.size})"
        }
    }

    private fun cerrarSesion() {
        requireContext()
            .getSharedPreferences("ecoruta_prefs", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        findNavController().navigate(R.id.loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}