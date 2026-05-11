package com.ecoruta.cafetera.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ecoruta.cafetera.R
import com.ecoruta.cafetera.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // Credenciales hardcodeadas para el taller
    private val USUARIOS = mapOf(
        "tecnico" to "1234",
        "admin"   to "admin123"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Si ya hay sesión activa, ir directo a fincas
        if (sesionActiva()) {
            navegar()
            return
        }

        binding.btnLogin.setOnClickListener {
            val usuario = binding.etUsuario.text.toString().trim()
            val contrasena = binding.etContrasena.text.toString().trim()
            intentarLogin(usuario, contrasena)
        }
    }

    private fun intentarLogin(usuario: String, contrasena: String) {
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Completa todos los campos")
            return
        }

        val claveEsperada = USUARIOS[usuario]
        if (claveEsperada != null && claveEsperada == contrasena) {
            guardarSesion(usuario)
            navegar()
        } else {
            mostrarError("Usuario o contraseña incorrectos")
        }
    }

    private fun guardarSesion(usuario: String) {
        val prefs = requireContext()
            .getSharedPreferences("ecoruta_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("sesion_activa", true)
            .putString("usuario_actual", usuario)
            .apply()
    }

    private fun sesionActiva(): Boolean {
        val prefs = requireContext()
            .getSharedPreferences("ecoruta_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("sesion_activa", false)
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