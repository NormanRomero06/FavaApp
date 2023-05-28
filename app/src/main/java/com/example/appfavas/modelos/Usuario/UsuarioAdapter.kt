package com.example.appfavas.modelos.Usuario

import android.annotation.SuppressLint
import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemUsuariosBinding


class UsuarioAdapter(private val userList: ArrayList<Usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemUsuariosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun load(item: Usuario) {
            with(binding) {
                tvidC.text = item.idUsuario.toString()
                tvNombres.text = "${item.nombres} ${item.apellidos}"
                tvEmail.text = item.correo
                tvRol.text = item.rol
                cvUsuarios.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("idUsuario", item.idUsuario.toString())
                    bundle.putString("nombres", item.nombres)
                    bundle.putString("apellidos", item.apellidos)
                    bundle.putString("correo", item.correo)
                    bundle.putString("usuario", item.usuario)
                    bundle.putString("contraseña", item.contraseña)
                    bundle.putString("rol", item.rol)
                    Navigation.findNavController(binding.root).navigate(R.id.editarUsuarioFragment, bundle)



                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItem =
            ItemUsuariosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userItem)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.load(this.userList[position])
    }
}
/*class UsuarioAdapter(private val onItemClick: (Usuario) -> Unit) :
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
}*/