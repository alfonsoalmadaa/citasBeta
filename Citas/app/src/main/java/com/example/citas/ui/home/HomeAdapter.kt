package com.example.citas.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citas.R

class HomeAdapter(val context: Context, private var personaList: MutableList<HomeViewModel>):
        RecyclerView.Adapter<HomeAdapter.PersonaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false)
        return PersonaViewHolder(view)
    }

    override fun getItemCount() = personaList.size

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        holder.bindView(personaList[position])
    }

    class PersonaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
        val textViewLocal = itemView.findViewById<TextView>(R.id.textViewLocal)
        val textViewDireccion = itemView.findViewById<TextView>(R.id.textViewDireccion)

        fun bindView(persona: HomeViewModel) {
            textViewNombre.text = persona.nome
            textViewLocal.text = persona.local
            textViewDireccion.text = persona.direccion
        }
    }

}