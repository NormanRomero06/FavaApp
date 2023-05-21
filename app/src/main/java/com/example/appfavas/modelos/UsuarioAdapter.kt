package com.example.appfavas.modelos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfavas.R
import com.example.appfavas.databinding.ItemUsuariosBinding

/*
class UsuarioAdapter(usuario:List<Usuario>, ctx: Context): RecyclerView.Adapter<UsuarioAdapter.ViewHolder>(){
    var usuario:List<Usuario>?=null
    var ctx:Context?=null

    init {
        this.usuario = usuario
        this.ctx = ctx
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vistaUsuario:View = LayoutInflater
            .from(ctx)
            .inflate(R.layout.item_usuarios, parent, false)

        val usuarioVH = ViewHolder(vistaUsuario)

        vistaUsuario.tag = usuarioVH
        return usuarioVH
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = usuario!!.get(position)
        holder.tvNombres!!.text = usuario.nombres
        holder.tvApellidos!!.text = usuario.apellidos
        holder.tvEmail!!.text = usuario.correo
        holder.tvRol!!.text = usuario.rol
    }

    override fun getItemCount(): Int {
        return usuario!!.size
    }

    class ViewHolder(vista: View):RecyclerView.ViewHolder(vista){
        var tvNombres: TextView?=null
        var tvApellidos: TextView?=null
        var tvEmail: TextView?=null
        var tvRol: TextView?=null

        init {
            tvNombres = vista.findViewById(R.id.tvNombres)
            tvApellidos = vista.findViewById(R.id.tvApellidos)
            tvEmail = vista.findViewById(R.id.tvEmail)
            tvRol = vista.findViewById(R.id.tvRol)

        }
    }*/





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

        fun bind(usuario: Usuario) {
            binding.tvNombres.text = "${usuario.nombres} ${usuario.apellidos}"
            binding.tvEmail.text = usuario.correo
            binding.tvRol.text = usuario.rol

            binding.root.setOnClickListener { onItemClick(usuario) }
        }
    }
}