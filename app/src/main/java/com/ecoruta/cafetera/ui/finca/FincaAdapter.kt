package com.ecoruta.cafetera.ui.finca

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecoruta.cafetera.data.local.entity.FincaEntity
import com.ecoruta.cafetera.databinding.ItemFincaBinding

class FincaAdapter(
    private val onEliminar: (FincaEntity) -> Unit
) : ListAdapter<FincaEntity, FincaAdapter.FincaViewHolder>(DiffCallback()) {

    inner class FincaViewHolder(private val binding: ItemFincaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(finca: FincaEntity) {
            binding.tvNombreFinca.text = finca.nombreFinca
            binding.tvPropietario.text = "Propietario: ${finca.nombrePropietario}"
            binding.tvMunicipio.text = " --${finca.municipio}"
            binding.tvTipoCultivo.text = "-> ${finca.tipoCultivo} · ${finca.hectareas} ha"
            binding.btnEliminar.setOnClickListener { onEliminar(finca) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FincaViewHolder {
        val binding = ItemFincaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FincaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FincaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<FincaEntity>() {
        override fun areItemsTheSame(a: FincaEntity, b: FincaEntity) = a.id == b.id
        override fun areContentsTheSame(a: FincaEntity, b: FincaEntity) = a == b
    }
}