package com.ecoruta.cafetera.ui.finca

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ecoruta.cafetera.data.local.entity.FincaEntity
import com.ecoruta.cafetera.databinding.FragmentFincaFormBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FincaFormFragment : Fragment() {

    private var _binding: FragmentFincaFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FincaViewModel by activityViewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    private val pedirPermiso = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) obtenerUbicacion()
        else mostrarError("Permiso de ubicación denegado")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFincaFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.btnGps.setOnClickListener {
            verificarYObtenerUbicacion()
        }

        binding.btnGuardar.setOnClickListener {
            guardarFinca()
        }
    }

    private fun verificarYObtenerUbicacion() {
        val permiso = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(requireContext(), permiso)
            == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacion()
        } else {
            pedirPermiso.launch(permiso)
        }
    }

    private fun obtenerUbicacion() {
        binding.tvCoordenadas.text = "Obteniendo ubicación..."
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latitud = location.latitude
                    longitud = location.longitude
                    binding.tvCoordenadas.text =
                        "Lat: %.6f  Lon: %.6f".format(latitud, longitud)
                    binding.tvCoordenadas.setTextColor(
                        ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark)
                    )
                } else {
                    binding.tvCoordenadas.text = "No se pudo obtener ubicación"
                }
            }
        } catch (e: SecurityException) {
            mostrarError("Error de permiso GPS")
        }
    }

    private fun guardarFinca() {
        val nombre = binding.etNombreFinca.text.toString().trim()
        val propietario = binding.etPropietario.text.toString().trim()
        val municipio = binding.etMunicipio.text.toString().trim()
        val tipoCultivo = binding.etTipoCultivo.text.toString().trim()
        val hectareasStr = binding.etHectareas.text.toString().trim()

        // Validación RF-12
        if (nombre.isEmpty() || propietario.isEmpty() ||
            municipio.isEmpty() || tipoCultivo.isEmpty() || hectareasStr.isEmpty()) {
            mostrarError("Todos los campos son obligatorios")
            return
        }

        val hectareas = hectareasStr.toDoubleOrNull()
        if (hectareas == null || hectareas <= 0) {
            mostrarError("Ingresa un valor válido de hectáreas")
            return
        }

        val finca = FincaEntity(
            nombreFinca = nombre,
            nombrePropietario = propietario,
            municipio = municipio,
            tipoCultivo = tipoCultivo,
            hectareas = hectareas,
            latitud = latitud,
            longitud = longitud,
            tecnicoRegistra = viewModel.tecnicoActual
        )
        viewModel.guardarFinca(finca)
        findNavController().popBackStack()
    }

    private fun mostrarError(mensaje: String) {
        binding.tvError.text = mensaje
        binding.tvError.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}