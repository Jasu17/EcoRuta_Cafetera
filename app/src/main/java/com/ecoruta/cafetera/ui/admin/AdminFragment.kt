package com.ecoruta.cafetera.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecoruta.cafetera.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AdminViewModel by viewModels()
    private lateinit var adapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        configurarRecycler()
        observarUsuarios()

        binding.fabAgregarUsuario.setOnClickListener {
            mostrarDialogoNuevoUsuario()
        }
    }

    private fun configurarRecycler() {
        adapter = UsuarioAdapter(
            onToggleEstado = { usuario ->
                viewModel.toggleEstado(usuario)
            }
        )
        binding.rvUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsuarios.adapter = adapter
    }

    private fun observarUsuarios() {
        viewModel.usuarios.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }
    }

    private fun mostrarDialogoNuevoUsuario() {
        val dialogBinding = com.ecoruta.cafetera.databinding.DialogNuevoUsuarioBinding
            .inflate(layoutInflater)

        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Nuevo técnico")
            .setView(dialogBinding.root)
            .setNegativeButton("Cancelar") { d, _ -> d.dismiss() }
            .setPositiveButton("Crear") { _, _ ->
                val username = dialogBinding.etUsername.text.toString().trim()
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val contrasena = dialogBinding.etContrasena.text.toString().trim()

                if (username.isNotEmpty() && nombre.isNotEmpty() && contrasena.isNotEmpty()) {
                    viewModel.crearTecnico(username, nombre, contrasena)
                }
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}