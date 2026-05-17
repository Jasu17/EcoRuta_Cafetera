package com.ecoruta.cafetera.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ecoruta.cafetera.R
import com.ecoruta.cafetera.data.local.AppDatabase
import com.ecoruta.cafetera.databinding.FragmentLoginBinding
import com.ecoruta.cafetera.utils.SessionManager
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SessionManager.sesionActiva(requireContext())) {
            navegar()
            return
        }

        binding.btnLogin.setOnClickListener {
            val usuario = binding.etUsuario.text.toString().trim()
            val contrasena = binding.etContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                mostrarError("Completa todos los campos")
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val dao = AppDatabase.getInstance(requireContext()).usuarioDao()
                val usuarioEncontrado = dao.login(usuario, contrasena)

                if (usuarioEncontrado != null) {
                    SessionManager.guardarSesion(
                        requireContext(),
                        usuarioEncontrado.username,
                        usuarioEncontrado.rol
                    )
                    navegar()
                } else {
                    mostrarError("Usuario o contraseña incorrectos")
                }
            }
        }
    }

    private fun mostrarError(mensaje: String) {
        binding.tvError.text = mensaje
        binding.tvError.visibility = View.VISIBLE
    }

    private fun navegar() {
        findNavController().navigate(R.id.action_login_to_fincas)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}