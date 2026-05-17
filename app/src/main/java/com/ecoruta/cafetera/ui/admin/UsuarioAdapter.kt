package com.ecoruta.cafetera.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecoruta.cafetera.data.local.entity.UsuarioEntity
import com.ecoruta.cafetera.databinding.ItemUsuarioBinding

class UsuarioAdapter(
    private val onToggleEstado: (UsuarioEntity) -> Unit
) : ListAdapter<UsuarioEntity, UsuarioAdapter.UsuarioViewHolder>(DiffCallback()) {

    inner class UsuarioViewHolder(private val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(usuario: UsuarioEntity) {
            binding.tvUsername.text = usuario.username
            binding.tvNombre.text = usuario.nombre
            binding.tvRol.text = usuario.rol.replaceFirstChar { it.uppercase() }
            binding.switchActivo.isChecked = usuario.activo
            binding.switchActivo.setOnCheckedChangeListener(null)
            binding.switchActivo.setOnCheckedChangeListener { _, _ ->
                onToggleEstado(usuario)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<UsuarioEntity>() {
        override fun areItemsTheSame(a: UsuarioEntity, b: UsuarioEntity) = a.username == b.username
        override fun areContentsTheSame(a: UsuarioEntity, b: UsuarioEntity) = a == b
    }
}