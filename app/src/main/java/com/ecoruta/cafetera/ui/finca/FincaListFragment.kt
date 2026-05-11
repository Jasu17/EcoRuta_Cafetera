package com.ecoruta.cafetera.ui.finca

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecoruta.cafetera.R
import com.ecoruta.cafetera.databinding.FragmentFincaListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FincaListFragment : Fragment() {

    private var _binding: FragmentFincaListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FincaViewModel by activityViewModels()
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

        binding.toolbar.inflateMenu(R.menu.menu_finca_list)
        binding.toolbar.menu.findItem(R.id.action_logout)?.icon?.setTint(
            android.graphics.Color.WHITE
        )
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_logout -> { cerrarSesion(); true }
                else -> false
            }
        }

        configurarRecycler()   // primero el adapter
        observarFincas()       // luego el observe

        binding.fabAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_form)
        }
    }

    private fun configurarRecycler() {
        adapter = FincaAdapter { finca ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Eliminar finca")
                .setMessage("¿Deseas eliminar la finca '${finca.nombreFinca}'? Esta acción no se puede deshacer.")
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Eliminar") { _, _ -> viewModel.eliminarFinca(finca) }
                .show()
        }
        binding.rvFincas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFincas.adapter = adapter
    }

    private fun observarFincas() {
        viewModel.fincas.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
            binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
            binding.toolbar.title = if (lista.isEmpty())
                "Mis Fincas"
            else
                "Mis Fincas (${lista.size})"
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