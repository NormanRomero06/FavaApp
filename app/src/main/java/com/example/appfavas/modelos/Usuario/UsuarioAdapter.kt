package com.example.appfavas.modelos.Usuario

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.databinding.ItemUsuariosBinding


class UsuarioAdapter(private val onItemClick: (Usuario) -> Unit) :
    RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    private var usuarios = listOf<Usuario>()

    fun setUsuarios(usuarios: List<Usuario>) {
        this.usuarios = usuarios
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuariosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(usuarios[position])
    }

    override fun getItemCount(): Int = usuarios.size

    inner class UsuarioViewHolder(private val binding: ItemUsuariosBinding, private val onItemClick: (Usuario) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(usuario: Usuario) {
            binding.tvNombres.text = "${usuario.nombres} ${usuario.apellidos}"
            binding.tvEmail.text = usuario.correo
            binding.tvRol.text = usuario.rol

            binding.root.setOnClickListener { onItemClick(usuario) }
        }
    }
}