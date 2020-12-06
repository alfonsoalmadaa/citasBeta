package com.example.citas.ui.busqueda_negocios

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citas.R

class busquedaNegociosAdapter(val context: Context, private var personaList: MutableList<busquedaNegociosViewModel>):
        RecyclerView.Adapter<busquedaNegociosAdapter.PersonaViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.busquedanegocios_item, parent, false)

        var viewHolder = PersonaViewHolder(view)
        view.setOnClickListener {
            val data = Bundle()
            data.putString("data",view.findViewById<TextView>(R.id.id_local).text.toString() )
            findNavController(it).navigate(R.id.action_busquedaNegociosFragment_to_informacionNegociosFragment,data)
        }

        return PersonaViewHolder(view)
    }

    override fun getItemCount() = personaList.size

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        holder.bindView(personaList[position])
    }

    class PersonaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewLocal = itemView.findViewById<TextView>(R.id.textViewLocal)
        val textViewDireccion = itemView.findViewById<TextView>(R.id.textViewDireccion)
        val textViewDistancia = itemView.findViewById<TextView>(R.id.textViewDistancia)
        val textViewIdLocal = itemView.findViewById<TextView>(R.id.id_local)

        fun bindView(persona: busquedaNegociosViewModel) {
            textViewLocal.text = persona.local
            textViewDireccion.text = persona.direccion
            textViewDistancia.text = persona.distancia
            textViewIdLocal.text = persona.id_local
        }
    }

}